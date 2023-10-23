package sample.city;

public class MapLocation {
	public String address;
	public String latitude;
	public String location_id;
	public String longitude;
	
	public MapLocation(String address, String latitude, String location_id, String longitude) {
		this.address = address;
		this.latitude = latitude;
		this.location_id = location_id;
		this.longitude = longitude;
	}
	public MapLocation() {
		super();
	}
}
