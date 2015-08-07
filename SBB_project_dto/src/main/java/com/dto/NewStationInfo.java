package com.dto;

public class NewStationInfo {
	private String station;
	private boolean isExist;
	public NewStationInfo(String station, boolean isExist) {
		super();
		this.station = station;
		this.isExist = isExist;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public boolean isExist() {
		return isExist;
	}
	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
	@Override
	public String toString() {
		return "NewStationInfo [station=" + station + ", isExist=" + isExist
				+ "]";
	}
	
}
