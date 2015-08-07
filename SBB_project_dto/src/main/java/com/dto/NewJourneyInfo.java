package com.dto;

public class NewJourneyInfo {
	private String routeInfo;
	private String date;
	private String time;
	private String journeyId;
	private String routeName;
	private String train;
	private boolean trainsLack;
	public NewJourneyInfo(String routeInfo, String date, String time,
			String journeyId, String routeName, String train, boolean trainsLack) {
		super();
		this.routeInfo = routeInfo;
		this.date = date;
		this.time = time;
		this.journeyId = journeyId;
		this.routeName = routeName;
		this.train = train;
		this.trainsLack = trainsLack;
	}
	public String getRouteInfo() {
		return routeInfo;
	}
	public void setRouteInfo(String routeInfo) {
		this.routeInfo = routeInfo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getJourneyId() {
		return journeyId;
	}
	public void setJourneyId(String journeyId) {
		this.journeyId = journeyId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getTrain() {
		return train;
	}
	public void setTrain(String train) {
		this.train = train;
	}
	public boolean isTrainsLack() {
		return trainsLack;
	}
	public void setTrainsLack(boolean trainsLack) {
		this.trainsLack = trainsLack;
	}
	@Override
	public String toString() {
		return "NewJourneyInfo [routeInfo=" + routeInfo + ", date=" + date
				+ ", time=" + time + ", journeyId=" + journeyId
				+ ", routeName=" + routeName + ", train=" + train
				+ ", trainsLack=" + trainsLack + "]";
	}
	
}
