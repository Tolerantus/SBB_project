package com.dto;

import java.util.List;

public class JourneysInfo {
private List<String> journeyStringData;
private List<String> journeyHelpInfo;
public JourneysInfo(List<String> journeyStringData, List<String> journeyHelpInfo) {
	super();
	this.journeyStringData = journeyStringData;
	this.journeyHelpInfo = journeyHelpInfo;
}
public List<String> getJourneyStringData() {
	return journeyStringData;
}
public void setJourneyStringData(List<String> journeyStringData) {
	this.journeyStringData = journeyStringData;
}
public List<String> getJourneyHelpInfo() {
	return journeyHelpInfo;
}
public void setJourneyHelpInfo(List<String> journeyHelpInfo) {
	this.journeyHelpInfo = journeyHelpInfo;
}
@Override
public String toString() {
	return "JourneysInfo [journeyStringData=" + journeyStringData
			+ ", journeyHelpInfo=" + journeyHelpInfo + "]";
}

}
