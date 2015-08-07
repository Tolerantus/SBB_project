package com.dto;

import java.util.List;

public class AllJourneysInfo {
private List<String> journeys;

	public AllJourneysInfo(List<String> journeys) {
		super();
		this.journeys = journeys;
	}

	public List<String> getJourneys() {
		return journeys;
	}

	public void setJourneys(List<String> journeys) {
		this.journeys = journeys;
	}

	@Override
	public String toString() {
		return "AllJourneysInfo [journeys=" + journeys + "]";
	}
	
}
