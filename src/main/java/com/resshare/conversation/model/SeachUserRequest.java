package com.resshare.conversation.model;

public class SeachUserRequest {
	private float create_date;
	private String token;
	private String user_name;

	// Getter Methods

	public float getCreate_date() {
		return create_date;
	}

	public String getToken() {
		return token;
	}

	public String getUser_name() {
		return user_name;
	}

	// Setter Methods

	public void setCreate_date(float create_date) {
		this.create_date = create_date;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}