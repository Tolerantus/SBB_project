package com.dto;

import java.util.List;

public class RequiredDataForNewRoute {
	private List<String> newRoute;
	private List<String> newDirections;
	private List<HoursMinutesCost> data;
	private String routeName;
	public RequiredDataForNewRoute(List<String> newRoute,
			List<String> newDirections, List<HoursMinutesCost> data,
			String routeName) {
		super();
		this.newRoute = newRoute;
		this.newDirections = newDirections;
		this.data = data;
		this.routeName = routeName;
	}
	public List<String> getNewRoute() {
		return newRoute;
	}
	public void setNewRoute(List<String> newRoute) {
		this.newRoute = newRoute;
	}
	public List<String> getNewDirections() {
		return newDirections;
	}
	public void setNewDirections(List<String> newDirections) {
		this.newDirections = newDirections;
	}
	public List<HoursMinutesCost> getData() {
		return data;
	}
	public void setData(List<HoursMinutesCost> data) {
		this.data = data;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	@Override
	public String toString() {
		return "RequiredDataForNewRoute [newRoute=" + newRoute
				+ ", newDirections=" + newDirections + ", data=" + data
				+ ", routeName=" + routeName + "]";
	}
	
	
}
