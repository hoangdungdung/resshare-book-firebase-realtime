package com.resshare.core.service;

import java.util.HashMap;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.FirebaseService;
import com.resshare.book.RefFireBaseBook;

import service.ServiceBase;

public class ResquestService extends ServiceBase {

	public ResquestService() {

	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {

		try {
			
		// token
		// user_id
		// refer_data
		// error
		// status
		// create_date
		String sKey = snapshot.getKey();
		String sKeyResponse =  snapshot.child("response_key").getValue(String.class);
		String sError = "";
		String sToken = sKey;
		String suser_id = snapshot.child("user_id").getValue(String.class);
		String sRefer_data = snapshot.child("refer_data").getValue(String.class);
		if ((sToken == null) || "".equals(sToken))
			sError = "token==null. ";
		if ((suser_id == null) || "".equals(suser_id))
			sError = sError + "user_id==null. ";
		if ((sRefer_data == null) || "".equals(sRefer_data))
			sError = sError + "refer_data==null.";
		HashMap< String, Object> hm=new HashMap<>();
		for (DataSnapshot iterable_element : snapshot.getChildren()) {
			
			hm.put(iterable_element.getKey(), iterable_element.getValue());
			
			
		}
		hm.put("create_date", ServerValue.TIMESTAMP);
		hm.put("token",sToken);
		
		if (!"".equals(sError)) {
			hm.put("error", sError);
			firebaseDb.getReference(RefFireBaseBook.REQUEST_ERROR).push().setValue(hm);
			
			firebaseDb.getReference(RefFireBaseBook.REQUEST).child(sKey).setValue(null);

		} else {

			firebaseDb.getReference(RefFireBaseBook.FIREBASE_USERS).child(sToken).child("user_id")
					.addValueEventListener(new ValueEventListener() {

						@Override
						public void onDataChange(DataSnapshot snapshotUser) {
							try {
								
							
							if(snapshotUser.exists())
							{
								String userId=snapshotUser.getValue(String.class);
								if(userId.equals(suser_id))
								{
									firebaseDb.getReference(RefFireBaseBook.REQUEST_HIS).push().setValue(hm);
									hm.remove("token");
									hm.remove("refer_data");
									
									firebaseDb.getReference(sRefer_data).push().setValue(hm);
									
									firebaseDb.getReference(RefFireBaseBook.REQUEST).child(sKey).setValue(null);
									
								}
								else
								{
									 
									hm.put("error", "user_id!=userId");
									firebaseDb.getReference(RefFireBaseBook.REQUEST_ERROR).push().setValue(hm);
									
									firebaseDb.getReference(RefFireBaseBook.REQUEST).child(sKey).setValue(null);
								}
								
							}
							
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onCancelled(DatabaseError error) {
							// TODO Auto-generated method stub

						}
					});

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
		return RefFireBaseBook.REQUEST;
	}

}
