package com.resshare.conversation.model;

public class SeachUserResponse {

	public SeachUserResponse() {
		// TODO Auto-generated constructor stub
	}
	 
	 private boolean find_status;
	 private String user_id;


	 // Getter Methods 

	 

	 public boolean getFind_status() {
	  return find_status;
	 }

	 public String getUser_id() {
	  return user_id;
	 }

	 // Setter Methods 

	 

	 public void setFind_status(boolean find_status) {
	  this.find_status = find_status;
	 }

	 public void setUser_id(String user_id) {
	  this.user_id = user_id;
	 }
	}