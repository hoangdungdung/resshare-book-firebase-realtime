package com.resshare.book;

import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;

public class DeleteAccountService extends ListenerBase {

	public static String delete_account = "../delete_account";

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				try {
					Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);

					Object app_user_id = objJs.get("app_user_id");

					FirebaseDatabase.getInstance().getReference(
							ResFirebaseReference.getDataPathReference(FirebaseRefOxy.profile) + "/" + app_user_id)
							.setValue(null);
					FirebaseDatabase.getInstance().getReference(
							ResFirebaseReference.getAbsolutePath(RefFireBaseBook.DATA_BOOK_ITEMS) + "/" + app_user_id)
							.addListenerForSingleValueEvent(new ValueEventListener() {

								@Override
								public void onDataChange(DataSnapshot snapshotDATA_BOOK_ITEMS) {
									if (snapshotDATA_BOOK_ITEMS.exists()) {
										Iterable<DataSnapshot> bookIds = snapshotDATA_BOOK_ITEMS.getChildren();
										for (DataSnapshot dataSnapshotBook : bookIds) {
											String bookId = dataSnapshotBook.getKey();
											String path = ResFirebaseReference
													.getAbsolutePath(RefFireBaseBook.BOOK_DATA_BOOK_USERS_NEW)
													+ "/" + bookId + "/" + app_user_id;
											System.out.print("remove account");
											System.out.print(path);
											
											FirebaseDatabase.getInstance()
													.getReference(path)
													.setValue(null);
										}
										FirebaseDatabase.getInstance()
												.getReference(ResFirebaseReference.getAbsolutePath(
														RefFireBaseBook.DATA_BOOK_ITEMS) + "/" + app_user_id)
												.setValue(null);
									}
									// TODO Auto-generated method stub

								}

								@Override
								public void onCancelled(DatabaseError error) {
									// TODO Auto-generated method stub

								}
							});
		 

				} catch (Exception e) {
					e.printStackTrace();
				}
				cleanInput(snapshot1);
				// FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
				// .child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(delete_account);
		// return "core/get_home_default_layout";
	}
}
