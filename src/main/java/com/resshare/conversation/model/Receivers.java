package com.resshare.conversation.model;



public class Receivers {
private String channel_id;
private Long latest_interactive_date;
private boolean read;



// Getter Methods 

public String getChannel_id() {
return channel_id;
}

public Long getLatest_interactive_date() {
return latest_interactive_date;
}

public boolean getRead() {
return read;
}

// Setter Methods 

public void setChannel_id(String channel_id) {
this.channel_id = channel_id;
}

public void setLatest_interactive_date(Long latest_interactive_date) {
this.latest_interactive_date = latest_interactive_date;
}

public void setRead(boolean read) {
this.read = read;
}
private String latest_message;
public void setLatest_message(String latest_message) {
	this.latest_message=latest_message;
	
}
public String getLatest_message() {
return latest_message;
}

private String latest_sender;
public void setLatest_sender(String latest_sender) {
	this.latest_sender=latest_sender;
	
}
public String getLatest_sender() {
return latest_sender;
}

}