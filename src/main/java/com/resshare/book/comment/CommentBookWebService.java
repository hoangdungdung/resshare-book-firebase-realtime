package com.resshare.book.comment;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;

import java.util.Date;

import service.ServiceBase;

/**
 * Listener (bản web) cho "book/input_data_cluster01/draft/book/data/commentBtn".
 *
 * Mỗi record mới = 1 comment HOẶC 1 reply, phân biệt bằng có field "comment_id" hay không:
 *  - KHÔNG có comment_id -> COMMENT, ghi vào:
 *      book/data/books/{owner_id}/{book_id}/comments/{pushId}
 *  - CÓ comment_id       -> REPLY, ghi vào:
 *      book/data/books/{owner_id}/{book_id}/comments/{comment_id}/replies/{pushId}
 *
 * Reply lưu PHẲNG dưới replies, cha-con thể hiện qua field "parentReplyId"
 * (client tự dựng cây bằng buildReplyTree). Field nội dung giữ camelCase để
 * phần hiển thị (BookCommentItem/BookReplyItem) đọc đúng.
 */
public class CommentBookWebService extends ServiceBase {

	final FirebaseDatabase database = FirebaseDatabase.getInstance();

	public CommentBookWebService() {
		super();
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot100, String previousChildName) {
		final String sKey = snapshot100.getKey();
		System.out.println("CommentBookWebService onChildAdded " + sKey);

		// Xử lý khi processing chưa có (null) hoặc khác "done"; chỉ bỏ qua khi đã "done"
		String processing = snapshot100.hasChild("processing")
				? snapshot100.child("processing").getValue(String.class)
				: null;
		if ("done".equals(processing))
			return;

		try {
			DataSnapshot data = snapshot100.child("data");

			final String ownerId = data.child("owner_id").getValue(String.class);
			final String bookId = data.child("book_id").getValue(String.class);
			String commentId = data.child("comment_id").getValue(String.class);
			String message = data.child("message").getValue(String.class);

			// Kênh trả response về client (qua resshare), giống search
			final String application = snapshot100.child("application").getValue(String.class);
			final String userIdResponse = snapshot100.child("user_id").getValue(String.class);
			final String eventResponse = snapshot100.child("event").getValue(String.class);

			System.out.println("CommentBookWebService parse"
					+ " | owner_id=" + ownerId
					+ " | book_id=" + bookId
					+ " | comment_id=" + commentId
					+ " | message=" + message
					+ " | rawData=" + data.getValue());

			if (ownerId == null || "".equals(ownerId) || bookId == null || "".equals(bookId)) {
				System.out.println("CommentBookWebService ERROR: thieu owner_id/book_id"
						+ " | owner_id=" + ownerId + " | book_id=" + bookId
						+ " | record=" + snapshot100.getValue());
				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
						.child("processing").setValue("error");
				return;
			}

			// Node nội dung (camelCase) - dùng chung cho cả comment lẫn reply
			Map<String, Object> node = new HashMap<>();
			node.put("userId", str(data.child("userId").getValue(String.class)));
			node.put("userEmail", str(data.child("userEmail").getValue(String.class)));
			node.put("userName", str(data.child("userName").getValue(String.class)));
			node.put("userAvatarUrl", str(data.child("userAvatarUrl").getValue(String.class)));
			node.put("message", str(data.child("message").getValue(String.class)));
			Long createdAt = data.child("createdAt").getValue(Long.class);
			node.put("createdAt", createdAt != null ? createdAt : System.currentTimeMillis());

			DatabaseReference target;
			if (commentId != null && !"".equals(commentId)) {
				// REPLY: lưu phẳng dưới replies của comment, kèm parentReplyId (cha-con)
				node.put("parentReplyId", str(data.child("parentReplyId").getValue(String.class)));
				target = database.getReference(RefFireBaseBook.BOOK_BOOKS)
						.child(ownerId).child(bookId)
						.child("comments").child(commentId).child("replies");
			} else {
				// COMMENT
				target = database.getReference(RefFireBaseBook.BOOK_BOOKS)
						.child(ownerId).child(bookId)
						.child("comments");
			}

			final boolean isReply = (commentId != null && !"".equals(commentId));
			target.push().setValue(node, (error, ref) -> {
				String state = (error == null) ? "done" : "error";
				if (error != null) {
					System.out.println("CommentBookWebService write ERROR: " + error.getMessage());
				} else {
					System.out.println("CommentBookWebService write OK ("
							+ (isReply ? "reply" : "comment") + ") -> " + ref);
					// Ghi xong -> đọc lại comments của sách rồi trả về client qua response (resshare)
					sendUpdatedComments(application, userIdResponse, eventResponse, ownerId, bookId);
				}
				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
						.child("processing").setValue(state);
			});

		} catch (Exception e) {
			System.out.println("CommentBookWebService EXCEPTION: " + e
					+ " | record=" + snapshot100.getValue());
			e.printStackTrace();
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
					.child("processing").setValue("error");
		}
	}

	private static String str(String s) {
		return s == null ? "" : s;
	}

	/**
	 * Đọc lại toàn bộ comments (gồm replies lồng) của 1 sách rồi trả về client
	 * qua response channel (resshare) với msg_type="comment_result".
	 * Client merge vào đúng sách trong filteredBooks (không thay cả danh sách).
	 */
	private void sendUpdatedComments(String application, String userIdResponse, String eventResponse,
			String ownerId, String bookId) {
		database.getReference(RefFireBaseBook.BOOK_BOOKS).child(ownerId).child(bookId).child("comments")
				.addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot commentsSnap) {
						try {
							Object comments = commentsSnap.getValue();

							DashboardMessage dashboardMessage = new DashboardMessage();
							dashboardMessage.setApplication(application);
							dashboardMessage.setDelete(0);
							dashboardMessage.setEvent(eventResponse);
							dashboardMessage.setUser_id_destination(userIdResponse);

							Map msgDashboardMessage = dashboardMessage.totHashMap();

							Map<String, Object> data = new HashMap<>();
							data.put("book_id", bookId);
							data.put("comments", comments);

							Map script_param = new HashMap<>();
							script_param.put("grid_view_layout_item", "grid_view_layout_item");
							data.put("script", com.resshare.book.search.SearchBookUI.class.getName());
							data.put("script_param", script_param);

							msgDashboardMessage.put("msg_type", "comment_result");
							msgDashboardMessage.put("data", data);
							msgDashboardMessage.put("id", String.valueOf(new Date().getTime()));

							System.out.println("CommentBookWebService comment_result"
									+ " | user_id_destination=" + userIdResponse
									+ " | event=" + eventResponse
									+ " | book_id=" + bookId);

							ResponseClient.sendResponseScriptUI(msgDashboardMessage);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onCancelled(DatabaseError error) {
						System.out.println("sendUpdatedComments onCancelled " + error.getMessage());
					}
				});
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		System.out.println("onChildChanged" + snapshot.getKey());
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		System.out.println("onChildRemoved" + snapshot.getKey());
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		System.out.println("onChildMoved" + snapshot.getKey());
	}

	@Override
	public void onCancelled(DatabaseError error) {
		System.out.println("onCancelled" + error.getMessage());
	}

	@Override
	public String getReferenceName() {
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.BOOK_COMMENT_BTN);
	}

}
