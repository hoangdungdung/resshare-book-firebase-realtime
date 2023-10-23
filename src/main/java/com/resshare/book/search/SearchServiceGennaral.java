package com.resshare.book.search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.sshare.core.StringUtil;

import model.Dashboard;
import model.RequestUsers;
import model.User;
import model.UserDistance;
import service.ServiceBase;

public  class SearchServiceGennaral  extends ServiceBase {

	private final static int TIME_CHECK = 5000;
	 

	private Map<String, String> mKeyName;

//	public abstract String getInterestTableName();
	public SearchServiceGennaral() {
		 
		super();
		FirebaseDatabase.getInstance().getReference(getReferenceName()).setValue(null);
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		System.err.println("onChildAdded " + snapshot.getKey());
		//data/type_seach
		String type_seach = snapshot.child("data/type_seach").getValue(String.class);
		String searching_collection = ResFirebaseReference.getInputPathReference("../searching/" + type_seach);
		FirebaseDatabase.getInstance().getReference(searching_collection).child(snapshot.getKey()).setValue(snapshot.getValue());
		
		
		FirebaseDatabase.getInstance().getReference().child(getReferenceName()).child(snapshot.getKey()).removeValue();
		
		
	}

	public String getReferenceName()
	{
		return  ResFirebaseReference.getInputPathReference(RefFireBaseBook.SEARCH_GENERAL); 
				//"../requets/searching");
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

}
