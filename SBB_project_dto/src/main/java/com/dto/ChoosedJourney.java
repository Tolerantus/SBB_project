package com.dto;

import java.util.List;

public class ChoosedJourney {
private String journeyId;
private List<String> journeys;
private Integer EmptySeats;
public ChoosedJourney(String journeyId, List<String> journeys, Integer emptySeats) {
	super();
	this.journeyId = journeyId;
	this.journeys = journeys;
	EmptySeats = emptySeats;
}
public String getJourneyId() {
	return journeyId;
}
public void setJourneyId(String journeyId) {
	this.journeyId = journeyId;
}
public List<String> getJourneys() {
	return journeys;
}
public void setJourneys(List<String> journeys) {
	this.journeys = journeys;
}
public Integer getEmptySeats() {
	return EmptySeats;
}
public void setEmptySeats(Integer emptySeats) {
	EmptySeats = emptySeats;
}
@Override
public String toString() {
	return "ChoosedJourney [journeyId=" + journeyId + ", journeys=" + journeys
			+ ", EmptySeats=" + EmptySeats + "]";
}

}
