package com.dto;

import java.util.List;

public class JourneyAndPassengers {
	private String journeyInfo;
	private List<String> passInfo;
	public JourneyAndPassengers(String journeyInfo, List<String> passInfo) {
		super();
		this.journeyInfo = journeyInfo;
		this.passInfo = passInfo;
	}
	public String getJourneyInfo() {
		return journeyInfo;
	}
	public void setJourneyInfo(String journeyInfo) {
		this.journeyInfo = journeyInfo;
	}
	public List<String> getPassInfo() {
		return passInfo;
	}
	public void setPassInfo(List<String> passInfo) {
		this.passInfo = passInfo;
	}
	@Override
	public String toString() {
		return "JourneyAndPassengers [journeyInfo=" + journeyInfo
				+ ", passInfo=" + passInfo + "]";
	}
	
}
