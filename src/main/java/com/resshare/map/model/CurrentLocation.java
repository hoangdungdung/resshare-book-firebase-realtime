package com.resshare.map.model;

public class CurrentLocation {
	public boolean isTableTrip;
	public String lat_degree;
	public String lng_degree;
	public double latitude;
	public double longitude;
	
	public CurrentLocation(boolean isTableTrip, String lat_degree, String lng_degree, double latitude,
			double longitude) {
		this.isTableTrip = isTableTrip;
		this.lat_degree = lat_degree;
		this.lng_degree = lng_degree;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public CurrentLocation() {
		super();
	}
}
