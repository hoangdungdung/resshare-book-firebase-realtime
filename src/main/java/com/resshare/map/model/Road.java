package com.resshare.map.model;

import java.util.List;

/**
 * Created by hung on 11/10/2017.
 */

public class Road {
    public String userId;
    public String userEmail;
    public double latitude;
    public double longitude;
    public String lat_degree;
    public String lng_degree;
    public String next_point;
    public String pre_point;
    public String idTrip;
    public String idTripCurrentUser;
    public boolean isTableTrip; // True is Trip, False is Trip_location
    public String timeStart;
    public int index;
    public List<Integer> lstIndex;

    public Road() {
    }

    public Road(String userId, String userEmail, double latitude, double longitude, String lat_degree, String lng_degree, String next_point, String pre_point, String idTrip, String idTripCurrentUser, boolean isTableTrip, String timeStart, int index,List<Integer> lstIndex) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lat_degree = lat_degree;
        this.lng_degree = lng_degree;
        this.next_point = next_point;
        this.pre_point = pre_point;
        this.idTrip = idTrip;
        this.idTripCurrentUser = idTripCurrentUser;
        this.isTableTrip = isTableTrip;
        this.timeStart = timeStart;
        this.index = index;
        this. lstIndex= lstIndex;
        
    }
}