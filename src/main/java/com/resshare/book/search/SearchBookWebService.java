package com.resshare.book.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.sshare.core.StringUtil;

import service.ServiceBase;

/**
 * Listener (bản web) cho "book/input_data_cluster01/draft/book/data/searchBtn".
 *
 * Mỗi record mới: lấy data/search_other_book_name, tìm trong "book/data/books"
 * các sách CỦA NGƯỜI KHÁC có book_name gần giống, rồi trả danh sách data về client
 * qua ResponseClient.sendResponseScriptUI (client React tự fill vào feed search.json).
 *
 * Khác bản Android (SearchBookService): không build layout, chỉ trả data thuần.
 */
public class SearchBookWebService extends ServiceBase {

	final FirebaseDatabase database = FirebaseDatabase.getInstance();

	public SearchBookWebService() {
		super();
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot100, String previousChildName) {
		final String sKey = snapshot100.getKey();
		System.out.println("SearchBookWebService onChildAdded " + sKey);
		// Xử lý khi processing chưa có (null) hoặc khác "done"; chỉ bỏ qua khi đã "done"
		String processing = snapshot100.hasChild("processing")
				? snapshot100.child("processing").getValue(String.class)
				: null;
		if ("done".equals(processing))
			return;

		try {
			// Người tìm (dùng để loại sách của chính họ, đồng thời là kênh trả response)
			final String searcherId = snapshot100.child("user_id").getValue(String.class);
			final String userIdResponse = searcherId;
			final String eventResponse = snapshot100.child("event").getValue(String.class);
			final String application = snapshot100.child("application").getValue(String.class);

			// Từ khoá tìm kiếm
			String textSearch = snapshot100.child("data").child("search_other_book_name").getValue(String.class);
				System.out.println("textSearch   " + textSearch);
			if (textSearch == null)
				textSearch = "";
			final String keySearch = StringUtil.removeAccent(textSearch).toLowerCase().trim();

			// Quét toàn bộ book/data/books (1 lần)
			database.getReference(RefFireBaseBook.BOOK_BOOKS)
					.addListenerForSingleValueEvent(new ValueEventListener() {

						@Override
						public void onDataChange(DataSnapshot booksRoot) {
							try {
								List<Map<String, Object>> results = new ArrayList<>();

								for (DataSnapshot ownerSnap : booksRoot.getChildren()) {
									String ownerId = ownerSnap.getKey();
									// Chỉ tìm sách của NGƯỜI KHÁC
									if (ownerId == null || ownerId.equals(searcherId))
										continue;

									for (DataSnapshot bookSnap : ownerSnap.getChildren()) {
										String bookName = bookSnap.child("book_name").getValue(String.class);
										if (bookName == null)
											continue;

										// So khớp gần đúng (bỏ dấu, không phân biệt hoa thường)
										String bookNameKey = StringUtil.removeAccent(bookName).toLowerCase();
										if (!"".equals(keySearch) && !bookNameKey.contains(keySearch))
											continue;

										String bookId = bookSnap.child("book_id").getValue(String.class);
											System.out.println("bookId   " + bookId);
										if (bookId == null)
											bookId = bookSnap.getKey();

										Map<String, Object> item = new HashMap<>();
										item.put("book_id", bookId);
										item.put("book_name", bookName);
										item.put("note", valueOrEmpty(bookSnap.child("note").getValue(String.class)));
										item.put("image_url",
												valueOrEmpty(bookSnap.child("image_url").getValue(String.class)));
										item.put("owner_id", ownerId);
										// Trả kèm comments (gồm cả replies lồng) để client hiển thị
										Object comments = bookSnap.child("comments").getValue();
										if (comments != null)
											item.put("comments", comments);
										results.add(item);
									}
								}

								sendResult(application, userIdResponse, eventResponse, results);

								// Đánh dấu đã xử lý xong
								FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
										.child("processing").setValue("done");
							} catch (Exception e) {
								e.printStackTrace();
								FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
										.child("processing").setValue("error");
							}
						}

						@Override
						public void onCancelled(DatabaseError error) {
							FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
									.child("processing").setValue("error");
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
					.child("processing").setValue("error");
		}
	}

	/** Trả danh sách kết quả về client (msg_type = search_book_result). */
	private static void sendResult(String application, String userIdResponse, String eventResponse,
			List<Map<String, Object>> books) {
 
		


		DashboardMessage dashboardMessage = new DashboardMessage();
		dashboardMessage.setApplication(application);
		dashboardMessage.setDelete(0);
		dashboardMessage.setEvent(eventResponse);
		dashboardMessage.setUser_id_destination(userIdResponse);

		Map msgDashboardMessage = dashboardMessage.totHashMap();

		Map<String, Object> data = new HashMap<>();
		data.put("books", books);
						
						
		Map script_param = new HashMap<>();
		script_param.put("grid_view_layout_item", "grid_view_layout_item");
		data.put("script", SearchBookUI.class.getName());
		data.put("script_param", script_param);





		msgDashboardMessage.put("msg_type", "search_book_result");
		msgDashboardMessage.put("data", data);
		msgDashboardMessage.put("id", String.valueOf(new Date().getTime()));

		System.out.println("SearchBookWebService sendResult"
				+ " | user_id_destination=" + userIdResponse
				+ " | event=" + eventResponse
				+ " | found=" + (books == null ? 0 : books.size())
				+ " | msg=" + msgDashboardMessage);

		ResponseClient.sendResponseScriptUI(msgDashboardMessage);
	}

	private static String valueOrEmpty(String s) {
		return s == null ? "" : s;
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
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.BOOK_SEARCH_BTN);
	}

}
