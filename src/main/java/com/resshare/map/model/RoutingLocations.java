package com.resshare.map.model;

public class RoutingLocations {


	RoutingEnd rountingEndObject;
	RoutingStart StartObject;

	// Getter Methods

	public RoutingEnd getEnd() {
		return rountingEndObject;
	}

	public RoutingStart getStart() {
		return StartObject;
	}

	// Setter Methods

	public void setEnd(RoutingEnd rountingEndObject) {
		this.rountingEndObject = rountingEndObject;
	}

	public void setStart(RoutingStart startObject) {
		this.StartObject = startObject;
	}
}

