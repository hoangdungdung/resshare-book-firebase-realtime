package com.resshare.conversation.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.conversation.model.SeachUserRequest;
import com.resshare.conversation.model.SeachUserResponse;
import com.sshare.core.StringUtil;

import model.User;
import service.ServiceBase;
import service.cache.Cache;

public class ConversationSearchUserService extends ServiceBase {

	public ConversationSearchUserService() {
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
try {
	

		SeachUserRequest seachUser = snapshot.getValue(SeachUserRequest.class);
		String user_nane = seachUser.getUser_name();
		String user_nane1 = StringUtil.removeAccent(user_nane);
		SeachUserResponse response = new SeachUserResponse();
		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.USERS_KEY).child(user_nane1).addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot1) {
				if(snapshot1.exists())
				{
					String userId =snapshot1.getValue(String.class);
					response.setUser_id(userId);
					response.setFind_status(true);
				}
				else
				{
					response.setFind_status(false);
					
				}
				FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_seach_user_response)
				.child(snapshot.getKey()).child("data").setValue(response);
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		if (Cache.mUserName.containsKey(user_nane1)) {
//			String userId = Cache.mUserName.get(user_nane1);
//
//			response.setUser_id(userId);
//			response.setFind_status(true);
//
//		} else {
//			response.setFind_status(false);
//		}
//
//		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_seach_user_response)
//				.child(snapshot.getKey()).child("data").setValue(response);
} catch (Exception e) {
	e.printStackTrace();
	// TODO: handle exception
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
		return RefFireBaseBook.conversation_seach_user;
	}
}
