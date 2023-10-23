package com.resshare.conversation.service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.conversation.model.ChannelMessage;
import com.resshare.conversation.model.Message;
import com.resshare.conversation.model.Receivers;

import service.ServiceBase;

public class MessageService extends ServiceBase {
	private final static int TIME_CHECK = 5000;
//	private Map<String, String> mKey = Cache.mUserChannelCache;

	public MessageService() {
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
	public void onChildAdded(DataSnapshot snapshotKey, String previousChildName) {

		// TODO Auto-generated method stub
		try {

			Message msg = snapshotKey.getValue(Message.class);
			String sKeyMessage =snapshotKey.getKey();

			String receiver_id = msg.getReceiver_id();
			String sender_id = msg.getSender_id();
			if (receiver_id == null)
				return;
			if (sender_id == null)
				return;

			String channelKey1 = sender_id + "_" + receiver_id;

			String channelKey2 = receiver_id + "_" + sender_id;
			
			// Boolean bNewChannel_id=false;

			Receivers receiver = new Receivers();
			
			FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_user_channel).child(channelKey1).addListenerForSingleValueEvent(new  ValueEventListener() {
				
				@Override
				public void onDataChange(DataSnapshot snapshot1) {
					try {
						String channel_id = "";
				
					if(snapshot1.exists())
					{
						channel_id = snapshot1.getValue(String.class);

						
					}
					else
					{
						channel_id = "CN" + FirebaseDatabase.getInstance()
						.getReference(RefFireBaseBook.conversation_channel_message).push().getKey();

				FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_user_channel).child(channelKey1)
						.setValue(channel_id);
				FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_user_channel).child(channelKey2)
						.setValue(channel_id);
						
						
					}
					
			
//			if (!Cache.mUserChannelCache.containsKey(channelKey1)) {
//				channel_id = "CN" + FirebaseDatabase.getInstance()
//						.getReference(RefFireBaseBook.conversation_channel_message).push().getKey();
//
//				FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_user_channel).child(channelKey1)
//						.setValue(channel_id);
//				FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_user_channel).child(channelKey2)
//						.setValue(channel_id);
//				// bNewChannel_id=true;
//
//			} else
//
//			{
//				channel_id = Cache.mUserChannelCache.get(channelKey1);
//
//			}

			ChannelMessage chnlMessage = new ChannelMessage();
			chnlMessage.setCreate_date(ServerValue.TIMESTAMP);
			chnlMessage.setReceiver_id(msg.getReceiver_id());
			chnlMessage.setSender_id(sender_id);
			chnlMessage.setMessage(msg.getMessage());
			chnlMessage.setType(msg.getType());
			
			
			DatabaseReference childChannel = FirebaseDatabase.getInstance()
					.getReference(RefFireBaseBook.conversation_channel_message).child(channel_id);
			String keyChannelMessage = childChannel.push().getKey();
			childChannel.child(keyChannelMessage).setValue(chnlMessage);

			receiver.setChannel_id(channel_id);
			receiver.setLatest_sender(sender_id);
			receiver.setLatest_interactive_date(msg.getCreate_date());
			receiver.setLatest_message(msg.getMessage());
			receiver.setRead(true);
			FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_receivers).child(sender_id)
					.child("list_receiver").child(receiver_id).setValue(receiver);

			receiver.setChannel_id(channel_id);
			receiver.setLatest_interactive_date(msg.getCreate_date());

			receiver.setRead(false);

			FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_receivers).child(receiver_id)
					.child("list_receiver").child(sender_id).setValue(receiver);

			FirebaseDatabase.getInstance()
					.getReference(RefFireBaseBook.conversation_receivers + "/" + receiver_id + "/chat_with")
					.addValueEventListener(new ValueEventListener() {

						@Override
						public void onDataChange(DataSnapshot snapshot2) {

							String userId = snapshot2.getValue(String.class);

							if (sender_id.equals(userId)) {
								receiver.setRead(true);
								FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_receivers)
										.child(receiver_id).child("list_receiver").child(sender_id).setValue(receiver);

							}

							DatabaseReference referReceiver = FirebaseDatabase.getInstance()
									.getReference(RefFireBaseBook.conversation_receivers + "/" + receiver_id);
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
								public void onChildAdded(DataSnapshot snapshot3, String previousChildName) {
									Long newMessageCount = 0L;
									String key = snapshot3.getKey();
									// if(key.equals("new_message_count"))
									// {
									// newMessageCount=snapshot.getValue(Long.class);
									//
									// }else
									if (key.equals("list_receiver")) {
										for (DataSnapshot snapshot13 : snapshot3.getChildren()) {
											Receivers recev = snapshot13.getValue(Receivers.class);

											if (!recev.getRead()) {
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

							// TODO Auto-generated method stub

						}

						@Override
						public void onCancelled(DatabaseError error) {
							// TODO Auto-generated method stub

						}
					});

			// DatabaseReference refer =
			// FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_receivers+"/"+sender_id+"/list_receiver/"+receiver_id+"/red");
			// if(refer==null) {

			// }

			// if(bNewChannel_id)
			// {
			//
			//
			// }
			//

			// DatabaseReference referReceiver =
			// FirebaseDatabase.getInstance().getReference(
			// RefFireBaseBook.conversation_receivers + "/" + receiver_id + "/list_receiver/" +
			// sender_id);

			FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_message).child(sKeyMessage)
					.removeValue();
			FirebaseDatabase.getInstance().getReference(RefFireBaseBook.conversation_message_his).child(sKeyMessage)
					.setValue(msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				@Override
				public void onCancelled(DatabaseError error) {
					// TODO Auto-generated method stub
					
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.conversation_message;
	}

}
