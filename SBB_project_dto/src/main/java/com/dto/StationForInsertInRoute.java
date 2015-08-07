package com.dto;

public class StationForInsertInRoute {
	private String newStation;
	private String selectedStation;
	private String step;
	private RouteStationList route;
	public StationForInsertInRoute(String newStation, String selectedStation,
			String step, RouteStationList route) {
		super();
		this.newStation = newStation;
		this.selectedStation = selectedStation;
		this.step = step;
		this.route = route;
	}
	public String getNewStation() {
		return newStation;
	}
	public void setNewStation(String newStation) {
		this.newStation = newStation;
	}
	public String getSelectedStation() {
		return selectedStation;
	}
	public void setSelectedStation(String selectedStation) {
		this.selectedStation = selectedStation;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public RouteStationList getRoute() {
		return route;
	}
	public void setRoute(RouteStationList route) {
		this.route = route;
	}
	@Override
	public String toString() {
		return "StationForInsertInRoute [newStation=" + newStation
				+ ", selectedStation=" + selectedStation + ", step=" + step
				+ ", route=" + route + "]";
	}
	
	
}
