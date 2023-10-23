package service;

import java.util.HashMap;

import com.book.service.ServicesManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tasks.Task;
import com.resshare.book.RefFireBaseBook;

import model.User;

public class FirebaseUsersService extends ServiceBase {
	// extends CacheServiceKeyBase<User> {

	public FirebaseUsersService() {
		super();
	}

	// @Override
	// public Map<String, User> getCacheObject() {
	// // TODO Auto-generated method stub
	// return Cache.mFirebaseUser;
	// }
	//
	// @Override
	// public Class<User> getClassT() {
	// // TODO Auto-generated method stub
	// return User.class;
	// }

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {
			User firebaseuser = snapshot.getValue(User.class);
			String firebaseuserId = snapshot.getKey();

			// super.onChildAdded(snapshot, previousChildName);

			String uid = snapshot.getKey();
			final FirebaseAuth auth = FirebaseAuth.getInstance();
			Task task = auth.getUser(uid).addOnSuccessListener(userRecord -> {
				System.out.println("Successfully fetched user data: " + userRecord.getUid());
				System.out.println("Successfully fetched user data: " + userRecord.getProviderData()[0].getEmail());

				String sEmail = userRecord.getProviderData()[0].getEmail();
				if (sEmail != null) {
					sEmail = sEmail.replace('.', '_');
					FirebaseDatabase.getInstance().getReference(RefFireBaseBook.PROVIDER_EMAIL).child(sEmail)
							.addValueEventListener(new ValueEventListener() {

								@Override
								public void onDataChange(DataSnapshot snapshot) {
									String user_id = null;
									String sEmail1 = snapshot.getKey();
									if (snapshot != null) {
										user_id = snapshot.getValue(String.class);
									}
									if ((snapshot == null) | (user_id == null)) {
										user_id = FirebaseDatabase.getInstance().getReference(RefFireBaseBook.USERS)
												.push().getKey();
										user_id = "US" + user_id;
										User user = new User();

										user.setAvatar(getAvatarUser(firebaseuser));
										user.setUser_name(firebaseuser.getUser_name());
										FirebaseDatabase.getInstance().getReference(RefFireBaseBook.USERS)
												.child(user_id).setValue(user);
										user.setUser_id(user_id);
										// Cache.mFirebaseUser.put(firebaseuserId, user);

										FirebaseDatabase.getInstance().getReference(RefFireBaseBook.PROVIDER_EMAIL)
												.child(sEmail1).setValue(user_id);
										createMsgDasboardWelcome(user_id);

									}
									FirebaseDatabase.getInstance().getReference(RefFireBaseBook.FIREBASE_USERS)
											.child(firebaseuserId).child("user_id").setValue(user_id);

									// TODO Auto-generated method stub

								}

								private void createMsgDasboardWelcome(String user_id) {

									HashMap<String, Object> hm = new HashMap<String, Object>();
									hm.put("type", "message.welcome");
									hm.put("create_time", ServerValue.TIMESTAMP);
									hm.put("text", "welcome");
									hm.put("refer_message", "system_settings/notify_mesage/welcome");

									FirebaseDatabase.getInstance().getReference(RefFireBaseBook.DASHBOARD)
											.child(user_id).child("welcome").setValue(hm);
								}

								@Override
								public void onCancelled(DatabaseError error) {
									// TODO Auto-generated method stub

								}
							});

				} else {
					String sPhone = userRecord.getProviderData()[0].getPhoneNumber();
					if (sPhone == null) {
						sPhone = userRecord.getProviderData()[0].getUid();
					}

					if (sPhone != null)

					{

						FirebaseDatabase.getInstance().getReference(RefFireBaseBook.PROVIDER_PHONE_NUMBER).child(sPhone)
								.addValueEventListener(new ValueEventListener() {

									@Override
									public void onDataChange(DataSnapshot snapshot) {
										String user_id = null;
										String sPhone = snapshot.getKey();
										if (snapshot != null) {
											user_id = snapshot.getValue(String.class);
										}
										if ((snapshot == null) | (user_id == null)) {
											user_id = FirebaseDatabase.getInstance().getReference(RefFireBaseBook.USERS)
													.push().getKey();
											user_id = "US" + user_id;
											User user = new User();

											user.setAvatar(getAvatarUser(firebaseuser));
											user.setUser_name(firebaseuser.getUser_name());
											FirebaseDatabase.getInstance().getReference(RefFireBaseBook.USERS)
													.child(user_id).setValue(user);
											user.setUser_id(user_id);
											/// Cache.mFirebaseUser.put(firebaseuserId, user);
											FirebaseDatabase.getInstance()
													.getReference(RefFireBaseBook.PROVIDER_PHONE_NUMBER).child(sPhone)
													.setValue(user_id);

										}
										FirebaseDatabase.getInstance().getReference(RefFireBaseBook.FIREBASE_USERS)
												.child(firebaseuserId).child("user_id").setValue(user_id);

										// TODO Auto-generated method stub

									}

									@Override
									public void onCancelled(DatabaseError error) {
										// TODO Auto-generated method stub

									}
								});

					}

				}

			}).addOnFailureListener(e -> {
				System.err.println("Error fetching user data: " + e.getMessage());
			});

			// See the UserRecord reference doc for the contents of userRecord.

		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		User firebaseuser = snapshot.getValue(User.class);
		User user = new User();
		user.setAvatar(getAvatarUser(firebaseuser));
		user.setUser_name(firebaseuser.getUser_name());
		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.USERS).child(firebaseuser.getUser_id())
				.setValue(user);

	}

	private String getAvatarUser(User firebaseuser) {
		if (firebaseuser != null)
			if ((firebaseuser.getAvatar() != null) && (!"".equals(firebaseuser.getAvatar())))
				return firebaseuser.getAvatar();
		return ServicesManager.avatar_default;
	}

	// @Override
	// public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
	// // TODO Auto-generated method stub
	// super.onChildAdded(snapshot, previousChildName);
	// User book = snapshot.getValue(getClassT());
	// String keyEncode = getKeyName(book);
	// FirebaseDatabase.getInstance().getReference().child(getTableNameKey()).child(keyEncode)
	// .setValue(snapshot.getKey());
	//
	// }

	// @Override
	// public void setId(String user_id, User book) {
	// book.setUser_id(user_id);
	//
	// }

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.FIREBASE_USERS;
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

	// @Override
	// public String getReferenceKeyName() {
	// // TODO Auto-generated method stub
	// return RefFireBaseBook.FIREBASE_USERS_KEY;
	// }

	// @Override
	// public String getKeyName(User obj) {
	// String keyEncode = obj.getUser_name();
	//
	// String keyEncode1 = StringUtil.removeAccent(keyEncode);
	// return keyEncode1;
	// }
}
