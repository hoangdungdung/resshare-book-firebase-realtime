package model;

import java.util.HashMap;

import com.google.firebase.database.Exclude;

public class UserDistance {
	@Exclude
	private String user_id;
	private String mail_address;
	private String user_name;
	private String address;
	private String status;
	private String phone_number;
	private String avatar;
	private String user_type;
	private String distance;

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	@Exclude
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

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Exclude
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

	private String getDistance() {

		return distance;
	}

	@Exclude
	public static HashMap<String, Object> getMapsUser(UserDistance user) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("user_name", user.getUser_name());
		maps.put("distance", user.getDistance());
		maps.put("mail_address", user.getMail_address());
		return maps;
	}

}
