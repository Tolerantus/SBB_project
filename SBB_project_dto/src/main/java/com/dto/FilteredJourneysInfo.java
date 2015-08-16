package com.dto;

import java.util.List;

public class FilteredJourneysInfo {
	private String start; 
	private String stop;
	private List<String> journeys;

	

	public FilteredJourneysInfo() {
		super();
	}

	public FilteredJourneysInfo(String start, String stop, List<String> journeys) {
		super();
		this.start = start;
		this.stop = stop;
		this.journeys = journeys;
	}

	public List<String> getJourneys() {
		return journeys;
	}

	public void setJourneys(List<String> journeys) {
		this.journeys = journeys;
	}

	
	
	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	@Override
	public String toString() {
		return "AllJourneysInfo [journeys=" + journeys + "]";
	}
	
}
