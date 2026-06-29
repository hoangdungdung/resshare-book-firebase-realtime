package com.resshare.book.bookcase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.LoadFormOxyBaseListener;

public class LoadFormMyBoocaseListener extends LoadFormOxyBaseListener {

	 
	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference("../load_form_script/my_bookcase");
		// FirebaseRefCovid19.draft_covid19 + "/load_form_script/" + getType();
	}

	@Override
	public String getReferenceNamePostData() {
		// "../draft/covid19/create_volunteers_group/post_data";
		  return  ResFirebaseReference.getInputPathReference(RefFireBaseBook.my_bookcase); 
		//"../requets/searching");
	}

	// @Override
	// public Script getScript() {
	// LoadFormSupplierOxyUI loadFormSupplierOxyUI = new LoadFormSupplierOxyUI();
	// return loadFormSupplierOxyUI.getUIBuilder().getScript();
	//
	// }
	public String getScriptName() {
		// TODO Auto-generated method stub
		return LoadFormMyBookcaseUI.class.getName();
	}

	private static String str(String s) {
		return s == null ? "" : s;
	}
	
	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);

				HashMap script_param = new HashMap<>();
				Object collection = getReferenceNamePostData();
				System.out.println("collection:" + String.valueOf(collection));
				// "../draft/covid19/create_volunteers_group/post_data";
				script_param.put("post_collection", collection);
				// objJs.put("user_id_destination", user_id);

				final Map mapReturnData = new HashMap<>();
//				LoadFormMyBookcaseUI loadFormMyBookcaseUI= new LoadFormMyBookcaseUI();
//				loadFormMyBookcaseUI.getUIBuilder().getScript();

				mapReturnData.put("script", getScriptName());
				mapReturnData.put("script_param", script_param);

				// Lấy sách của chính user (book/data/books/{user_id}) rồi đẩy vào mapReturnData.
				// user_id có thể nằm ở 'user_id' hoặc 'user_id_destination' tuỳ record.
				String userIdTmp = snapshot1.child("user_id").getValue(String.class);
				if (userIdTmp == null || "".equals(userIdTmp))
					userIdTmp = snapshot1.child("user_id_destination").getValue(String.class);
				final String user_id = userIdTmp;

				System.out.println("LoadFormMyBoocaseListener user_id=" + user_id
						+ " | record=" + snapshot1.getValue());

				// Không xác định được user_id -> gửi form không kèm sách (tránh NPE)
				if (user_id == null || "".equals(user_id)) {
					objJs.put("data", mapReturnData);
					ResponseClient.sendResponseScriptUI(objJs);
					FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
							.child("processing").setValue("done");
					return;
				}

				// Fetch là async nên gửi response sau khi có sách (trong onDataChange).
				FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_BOOKS).child(user_id)
						.addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot myBooks) {
								try {
									List<Map<String, Object>> books = new ArrayList<>();
									for (DataSnapshot bookSnap : myBooks.getChildren()) {
										String bookId = bookSnap.child("book_id").getValue(String.class);
										if (bookId == null)
											bookId = bookSnap.getKey();

										Map<String, Object> item = new HashMap<>();
										item.put("book_id", bookId);
										item.put("book_name", str(bookSnap.child("book_name").getValue(String.class)));
										item.put("note", str(bookSnap.child("note").getValue(String.class)));
										item.put("image_url", str(bookSnap.child("image_url").getValue(String.class)));
										item.put("owner_id", user_id);
										Object comments = bookSnap.child("comments").getValue();
										if (comments != null)
											item.put("comments", comments);
										books.add(item);
									}
									mapReturnData.put("books", books);

									objJs.put("data", mapReturnData);
									System.out.println("LoadFormMyBoocaseListener event:" + objJs.get("event")
											+ " | user_id=" + user_id
											+ " | books=" + books.size()
											+ " | result=" + books);
									System.out.println("LoadFormMyBoocaseListener msg gửi đi=" + objJs);
									ResponseClient.sendResponseScriptUI(objJs);

									FirebaseDatabase.getInstance().getReference(getReferenceName())
											.child(snapshot1.getKey()).child("processing").setValue("done");
								} catch (Exception e) {
									e.printStackTrace();
									FirebaseDatabase.getInstance().getReference(getReferenceName())
											.child(snapshot1.getKey()).child("processing").setValue("error");
								}
							}

							@Override
							public void onCancelled(DatabaseError error) {
								// Lỗi đọc sách -> vẫn gửi form (không kèm sách)
								objJs.put("data", mapReturnData);
								ResponseClient.sendResponseScriptUI(objJs);
								FirebaseDatabase.getInstance().getReference(getReferenceName())
										.child(snapshot1.getKey()).child("processing").setValue("done");
							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
