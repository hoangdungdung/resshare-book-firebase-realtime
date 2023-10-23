package com.resshare.conversation.service;

import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.conversation.model.ChannelMessage;
import com.resshare.conversation.model.Message;
import com.resshare.conversation.model.Receivers;

import service.ServiceBase;
import service.cache.Cache;

public class MessageServiceCount extends ServiceBase {
	private final static int TIME_CHECK = 5000;
//	private Map<String, String> mKey = Cache.mUserChannelCache;

	public MessageServiceCount() {
		super();

	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {

	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {

		// TODO Auto-generated method stub

		Message msg = snapshot.getValue(Message.class);

		String receiver_id = msg.getReceiver_id();
		String sender_id = msg.getSender_id();
		 
		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_receivers).child(sender_id)
				.child("list_receiver").child(receiver_id).child("read").setValue(true);
		
		 
		FirebaseDatabase.getInstance().getReference().child("conversation").child("message_count").child(snapshot.getKey()).removeValue();
		FirebaseDatabase.getInstance().getReference().child("conversation").child("message_count_his").child(snapshot.getKey()).setValue(msg);
		
		  
		  
		
		DatabaseReference referReceiver = FirebaseDatabase.getInstance().getReference(
				RefFireBaseBook.conversation_receivers + "/" + sender_id);
		referReceiver.addChildEventListener(new ChildEventListener() {
			
			@Override
			public void onChildRemoved(DataSnapshot snapshot) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
				Long newMessageCount=0L;
				String key = snapshot.getKey();
//				if(key.equals("new_message_count"))
//				{
//					newMessageCount=snapshot.getValue(Long.class);
//					
//				}else
					if(key.equals("list_receiver"))
						{
						for (DataSnapshot snapshot1 : snapshot.getChildren()) {
							Receivers recev= snapshot1.getValue(Receivers.class);
						 
							
							if(!recev.getRead())
							{
								newMessageCount++;	
							}
							
						 
							
						}
						referReceiver.child("new_message_count").setValue(newMessageCount);
						
					
						}
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
				

		//DatabaseReference referReceiver = FirebaseDatabase.getInstance().getReference(
			//	RefFireBaseBook.conversation_receivers + "/" + receiver_id + "/list_receiver/" + sender_id);
		
		
		 
		 

	}

 

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.conversation_message_count;
	}

}
