//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.resshare;

import org.springframework.beans.factory.annotation.Value;

public class AppConfig {
    @Value("${database}")
    private String database;
    @Value("${app_name}")
    private String app_name;
    @Value("${backend_key}")
    private String backend_key;
    @Value("${backend_address}")
    private String backend_address;
    @Value("${debug}")
    private String debug;
    @Value("${ngrok}")
    private String ngrok;
    @Value("${gateway_type}")
    private String gateway_type;
    @Value("${gateway_uri}")
    private String gateway_uri;
    @Value("${x_api_key}")
    private String x_api_key;
    @Value("${http}")
    private String http;
    @Value("${cluster}")
    private String cluster;
    @Value("${user_id}")
    private String user_id;
    @Value("${upload_file_uri}")
    private String upload_file_uri;
    @Value("${x_api_key_upload_file}")
    private String x_api_key_upload_file;

    public AppConfig() {
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getApp_name() {
        return this.app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getBackend_key() {
        return this.backend_key;
    }

    public void setBackend_key(String backend_key) {
        this.backend_key = backend_key;
    }

    public String getBackend_address() {
        return this.backend_address;
    }

    public void setBackend_address(String backend_address) {
        this.backend_address = backend_address;
    }

    public String getDebug() {
        return this.debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getNgrok() {
        return this.ngrok;
    }

    public void setNgrok(String ngrok) {
        this.ngrok = ngrok;
    }

    public String getGateway_type() {
        return this.gateway_type;
    }

    public void setGateway_type(String gateway_type) {
        this.gateway_type = gateway_type;
    }

    public String getGateway_uri() {
        return this.gateway_uri;
    }

    public void setGateway_uri(String gateway_uri) {
        this.gateway_uri = gateway_uri;
    }

    public String getX_api_key() {
        return this.x_api_key;
    }

    public void setX_api_key(String x_api_key) {
        this.x_api_key = x_api_key;
    }

    public String getHttp() {
        return this.http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public String getCluster() {
        return this.cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUpload_file_uri() {
        return this.upload_file_uri;
    }

    public void setUpload_file_uri(String upload_file_uri) {
        this.upload_file_uri = upload_file_uri;
    }

    public String getX_api_key_upload_file() {
        return this.x_api_key_upload_file;
    }

    public void setX_api_key_upload_file(String x_api_key_upload_file) {
        this.x_api_key_upload_file = x_api_key_upload_file;
    }
}
