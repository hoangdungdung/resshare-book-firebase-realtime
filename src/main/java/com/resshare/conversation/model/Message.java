package com.resshare.conversation.model;

public class Message {
	private Long create_date;
	private String message;
	private String receiver_id;
	private String token;
	private String sender_id;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// Getter Methods

	public Long getCreate_date() {
		return create_date;
	}

	public String getMessage() {
		return message;
	}

	public String getReceiver_id() {
		return receiver_id;
	}

	public String getToken() {
		return token;
	}

	// Setter Methods

	public void setCreate_date(Long create_date) {
		this.create_date = create_date;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSender_id() {
		// TODO Auto-generated method stub
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

}
