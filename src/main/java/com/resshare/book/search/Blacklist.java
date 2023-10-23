 
package com.resshare.book.search;

import java.util.HashMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.ResFirebaseReference;

import service.ServiceBase;

public class Blacklist extends ServiceBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		// TODO Auto-generated method stub
		try {
			if (snapshot1.child("processing").getValue() == null) {
//owner_id

				String book_id = snapshot1.child("data/book_id").getValue(String.class);
				String owner_id = snapshot1.child("data/owner_id").getValue(String.class);
				String user_id = snapshot1.child("user_id").getValue(String.class);
				  
				FirebaseDatabase.getInstance().getReference()
						.child(ResFirebaseReference.getAbsolutePath(RefFireBaseBook.data_blacklist_user_book)).child(user_id).
						child(owner_id).setValue(book_id);
						
						
							}

						

			

			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
					.child("processing").setValue("done");

		} catch (Exception e) {
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
					.child("processing").setValue("error");
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
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.input_blacklist);
	}

}
