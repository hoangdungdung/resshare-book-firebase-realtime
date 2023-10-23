package com.resshare.book.bookcase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.ResFirebaseReference;

import service.ServiceBase;

public class RemoveBookItem  extends ServiceBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		// TODO Auto-generated method stub
		try {
			if (snapshot1.child("processing").getValue() == null) {
				
	String book_id = snapshot1.child("data/book_id").getValue(String.class);
	String book_items_id = snapshot1.child("data/book_items_id").getValue(String.class);
	String user_id = snapshot1.child("user_id").getValue(String.class);
	
	String DATA_BOOK_ITEMS=  ResFirebaseReference.getAbsolutePath(RefFireBaseBook.DATA_BOOK_ITEMS)+ "/"+ user_id+"/"+book_id+"/"+ book_items_id;
	 
	 
	FirebaseDatabase.getInstance().getReference(DATA_BOOK_ITEMS).setValue(null);
				
				 

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
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
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.remove_book_item);
	}

}
