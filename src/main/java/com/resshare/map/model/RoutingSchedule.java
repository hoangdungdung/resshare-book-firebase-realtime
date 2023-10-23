package com.resshare.map.model;

public class RoutingSchedule {
 
    private String time_start;
    private String user_email;
    private String user_id;
    private String routing_id;
    private String authen_id;
    private String status;
	public String getStatus( ) {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    public String getRoutingId() {
        return routing_id;
    }

    public void setRoutingId(String routingId) {
        this.routing_id = routingId;
    }


    public String getTimeStart() {
        return time_start;
    }

    public void setTimeStart(String timeStart) {
        this.time_start = timeStart;
    }

    public String getUserEmail() {
        return user_email;
    }

    public void setUserEmail(String userEmail) {
        this.user_email = userEmail;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

	public void setAuthenId(String authenId) {
		authen_id=authenId;
		
	}
	public String getAuthenId() {
        return authen_id;
    }
	
	
	
	  private Integer empty_seat;
	    public Integer getEmptySeat() {
	        return empty_seat;
	    }

	    // Setter Methods

	    public void setEmptySeat(Integer empty_seat) {
	        this.empty_seat = empty_seat;
	    }

	    private String vehicle_type;
	    public String getVehicleType() {
	        return vehicle_type;
	    }

	    // Setter Methods

	    public void setVehicleType(String vehicle_type) {
	        this.vehicle_type = vehicle_type;
	    }
}