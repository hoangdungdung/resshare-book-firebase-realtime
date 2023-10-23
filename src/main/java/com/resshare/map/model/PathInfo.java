package com.resshare.map.model;

public class PathInfo {
    private String fullAddress;
    private Double lat;
    private String lat_degree;
    private Double lng;
    private String lng_degree;

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getLat_degree() {
        return lat_degree;
    }

    public void setLat_degree(String lat_degree) {
        this.lat_degree = lat_degree;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getLng_degree() {
        return lng_degree;
    }

    public void setLng_degree(String lng_degree) {
        this.lng_degree = lng_degree;
    }
}