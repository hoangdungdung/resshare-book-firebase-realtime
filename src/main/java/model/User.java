package model;

import java.io.Serializable;
import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.annotations.SerializedName;

import listener.UserInfoListener;

public class User  implements Serializable {
	 
	private String user_id;
	private String mail_address;
	private String user_name;
	private String address;
	private String status;
	private String phone_number;
	private String avatar;
	private String user_type;

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}


	 
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setMail_address(String mail_address) {
		this.mail_address = mail_address;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

 
	public String getUser_id() {
		return user_id;
	}

	

	public String getMail_address() {
		return mail_address;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getAddress() {
		return address;
	}

	public String getStatus() {
		return status;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public String getAvatar() {
		return avatar;
	}
	
	@Exclude
	public static HashMap<String, Object> getMapsUser(User user) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("user_name", user.getUser_name());
		maps.put("avatar", user.getAvatar());
		return maps;
	}
	@Exclude
	public static HashMap<String, Object> getMapsUsers(User  user) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("user_name", user.getUser_name());
		maps.put("user_id",user.getUser_id());
		maps.put("avatar", user.getAvatar()); 
	 	maps.put("address", user.getAddress());
	 
	 
		return maps;

	}
	@Exclude
	public static void getUserInfo(String userID, UserInfoListener listener) {
		FirebaseDatabase.getInstance().getReference().child("users").child(userID)
				.addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot snapshot) {
						if (snapshot != null && snapshot.getValue() != null) {
							User user = snapshot.getValue(User.class);
							user.setUser_id(snapshot.getKey());
							listener.onGetUser(user);
						} else
							listener.onGetUser(null);

					}

					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub
						listener.onGetUser(null);
					}
				});
	}

}
