package service;

import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.resshare.book.RefFireBaseBook;

public class UsersService extends ServiceBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		createMsgDasboardWelcome(snapshot.getKey());
	}

	private void createMsgDasboardWelcome(String user_id) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("type", "message.welcome");
		hm.put("create_time", ServerValue.TIMESTAMP);
		hm.put("text", "welcome");
		hm.put("refer_message", "system_settings/notify_mesage/welcome");
  
		
		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.DASHBOARD).child(user_id).child("welcome").setValue(hm);
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
		return RefFireBaseBook.USERS;
	}
	//
	// public UsersService() {
	// super();
	// }
	//
	// @Override
	// public Class<User> getClassT() {
	// // TODO Auto-generated method stub
	// return User.class;
	// }
	//
	// @Override
	// public void setId(String user_id, User book) {
	// book.setUser_id(user_id);
	//
	// }
	//
	// @Override
	// public String getReferenceName() {
	// // TODO Auto-generated method stub
	// return RefFireBaseBook.USERS;
	// }
	//
	// @Override
	// public String getReferenceKeyName() {
	// // TODO Auto-generated method stub
	// return RefFireBaseBook.USERS_KEY;
	// }
	//
	// @Override
	// public String getKeyName(User obj) {
	// String keyEncode = obj.getUser_name();
	// String keyEncode1 = StringUtil.removeAccent(keyEncode);
	// return keyEncode1;
	// }

}
