package com.dto;

public class HoursMinutesCost {
	private String hours;
	private String minutes;
	private String cost;
	public HoursMinutesCost(String hours, String minutes, String cost) {
		super();
		this.hours = hours;
		this.minutes = minutes;
		this.cost = cost;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	@Override
	public String toString() {
		return "HoursMinutesCost [hours=" + hours + ", minutes=" + minutes
				+ ", cost=" + cost + "]";
	}
	
	
}
