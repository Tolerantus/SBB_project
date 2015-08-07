package com.dto;

import java.util.Map;

public class AllRoutesInfo {
	private Map<Integer,String> routes;

	public AllRoutesInfo(Map<Integer, String> routes) {
		super();
		this.routes = routes;
	}

	public Map<Integer, String> getRoutes() {
		return routes;
	}

	public void setRoutes(Map<Integer, String> routes) {
		this.routes = routes;
	}

	@Override
	public String toString() {
		return "AllRoutesInfo [routes=" + routes + "]";
	}
	
}
