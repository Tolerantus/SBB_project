package com.dto;

import java.util.List;

public class RouteStationList {
	private List<String> route;

	public RouteStationList(List<String> route) {
		super();
		this.route = route;
	}

	public List<String> getRoute() {
		return route;
	}

	public void setRoute(List<String> route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "RouteStationList [route=" + route + "]";
	}
	
}
