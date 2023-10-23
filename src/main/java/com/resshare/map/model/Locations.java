package com.resshare.map.model;

public class Locations {
	public String user_id;
	public String user_email;
	public String address;
	public String full_address;
	public double latitude;
	public double longitude;
	public String modify_address;
	public String latitude_degree;
	public String longitude_degree;
	 public String vehicle;
 
	public int type;

	public Locations() {

	}
	public Locations(String user_id, String user_email, String address, String full_address, double latitude,
			double longitude, String modify_address, String latitude_degree, String longitude_degree, int type) {
		this.user_id = user_id;
		this.user_email = user_email;
		this.address = address;
		this.full_address = full_address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.modify_address = modify_address;
		this.latitude_degree = latitude_degree;
		this.longitude_degree = longitude_degree;
		this.type = type;
	}		      

}