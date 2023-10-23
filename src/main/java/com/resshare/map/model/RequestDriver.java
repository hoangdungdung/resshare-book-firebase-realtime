package com.resshare.map.model;

public class RequestDriver {
    private Double distance;
    private PathInfo end_path;
    private PathInfo start_path;
    private String time_start;
    private String user_email;
    public String user_id;
    private String routing_id;
    private String status;

    public String full_address;


    public String getFull_address( ) {
        return this.full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }


    public double latitude;

    public double longitude;

    public double getLongitude( ) {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude( ) {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


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


    //empty_seat
    //vehicle_type
}