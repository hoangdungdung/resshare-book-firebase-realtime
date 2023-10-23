package com.resshare.map.model;

public class RoadTogetherOrder {

	public RoadTogetherOrder() {
		// TODO Auto-generated constructor stub
	}

	private String driver_user_id;
	private String partner_routing;
	private String partner_user_id;
	private float path_length;
	private float price;
	private String request_routing;
	private String request_user_id;
	private String token;
	private String vehicle_type;
	private String status;

	// Getter Methods

	public String getDriver_user_id() {
		return driver_user_id;
	}

	public String getPartner_routing() {
		return partner_routing;
	}

	public String getPartner_user_id() {
		return partner_user_id;
	}

	public float getPath_length() {
		return path_length;
	}

	public float getPrice() {
		return price;
	}

	public String getRequest_routing() {
		return request_routing;
	}

	public String getRequest_user_id() {
		return request_user_id;
	}

	public String getToken() {
		return token;
	}

	public String getVehicle_type() {
		return vehicle_type;
	}

	// Setter Methods

	public void setDriver_user_id(String driver_user_id) {
		this.driver_user_id = driver_user_id;
	}

	public void setPartner_routing(String partner_routing) {
		this.partner_routing = partner_routing;
	}

	public void setPartner_user_id(String partner_user_id) {
		this.partner_user_id = partner_user_id;
	}

	public void setPath_length(float path_length) {
		this.path_length = path_length;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setRequest_routing(String request_routing) {
		this.request_routing = request_routing;
	}

	public void setRequest_user_id(String request_user_id) {
		this.request_user_id = request_user_id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}
	
	public String getStatus( ) {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}