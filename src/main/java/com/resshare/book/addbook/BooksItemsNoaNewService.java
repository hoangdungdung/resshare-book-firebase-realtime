package com.resshare.book.addbook;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.ResFirebaseReference;

import service.ServiceBase;

/**
 * Listener cho "book/input_data_cluster01/draft/book/data/book_items_noa_new".
 * Mỗi record được thêm vào sẽ tạo 1 sách mới trong collection "book/books/{user_id}/{book_id}":
 *
 * {
 *   "book_id":    "<push_id>",
 *   "book_name":  "...",
 *   "image_name": "",
 *   "image_url":  "",
 *   "note":       "...",
 *   "status":     "New"
 * }
 */
public class BooksItemsNoaNewService extends ServiceBase {

	final FirebaseDatabase database = FirebaseDatabase.getInstance();

	public BooksItemsNoaNewService() {
		super();
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot100, String previousChildName) {
		// Chỉ xử lý record chưa được xử lý (processing == null)
		final String sKey = snapshot100.getKey();
		System.out.println("onChildAdded " + sKey);
		if (snapshot100.child("processing").getValue() != null)
			return;

		try {
			System.out.println("onChildAdded " + sKey);

			String user_id = snapshot100.child("user_id").getValue(String.class);
			if (user_id == null || "".equals(user_id)) {
				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
						.child("processing").setValue("error");
				return;
			}

			// Dữ liệu form gửi lên nằm trong "data" (key đã được map theo field/tên cột DB)
			DataSnapshot data = snapshot100.child("data");
			String book_name = data.child("book_name").getValue(String.class);
			String note = data.child("note").getValue(String.class);
			String image_name = data.child("image_name").getValue(String.class);
			String image_url = data.child("image_url").getValue(String.class);

			if (book_name == null) book_name = "";
			if (note == null) note = "";
			if (image_name == null) image_name = "";
			if (image_url == null) image_url = "";

			// Tạo book_id mới dưới book/books/{user_id}
			DatabaseReference refBooksByUser = database.getReference(RefFireBaseBook.BOOK_BOOKS).child(user_id);
			final String book_id = refBooksByUser.push().getKey();

			Map<String, Object> book = new HashMap<>();
			book.put("book_id", book_id);
			book.put("book_name", book_name);
			book.put("image_name", image_name);
			book.put("image_url", image_url);
			book.put("note", note);
			book.put("status", "New");

			refBooksByUser.child(book_id).setValue(book, (error, ref) -> {
				String state = (error == null) ? "done" : "error";
				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
						.child("processing").setValue(state);
			});

		} catch (Exception e) {
			e.printStackTrace();
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
					.child("processing").setValue("error");
		}
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
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA_NEW);
	}

}
