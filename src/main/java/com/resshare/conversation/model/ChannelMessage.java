package com.resshare.conversation.model;


public class ChannelMessage {
 private Object create_date;
 private String message;
 private String receiver_id;
 private boolean receiver_read;
 private String sender_id;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

 // Getter Methods 

 public Object getCreate_date() {
  return create_date;
 }

 public String getMessage() {
  return message;
 }

 public String getReceiver_id() {
  return receiver_id;
 }

 public boolean getReceiver_read() {
  return receiver_read;
 }

 public String getSender_id() {
  return sender_id;
 }

 // Setter Methods 

 public void setCreate_date(Object create_date) {
  this.create_date = create_date;
 }

 public void setMessage(String message) {
  this.message = message;
 }

 public void setReceiver_id(String receiver_id) {
  this.receiver_id = receiver_id;
 }

 public void setReceiver_read(boolean receiver_read) {
  this.receiver_read = receiver_read;
 }

 public void setSender_id(String sender_id) {
  this.sender_id = sender_id;
 }
}											

