package model;

import java.util.ArrayList;
import java.util.List;

public class RequestUsers {
	public String request_userId;
	public List<UserDistance> listUserDistance;

	public RequestUsers(String request_userId) {
		this.request_userId = request_userId;
		listUserDistance = new ArrayList<>();
	}

	public RequestUsers() {
		super();
		listUserDistance = new ArrayList<>();
	}
}
