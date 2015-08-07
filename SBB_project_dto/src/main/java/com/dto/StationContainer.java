package com.dto;

import java.util.List;

import com.entities.Station;

public class StationContainer {
private List<Station> stations;

public StationContainer(List<Station> stations) {
	super();
	this.stations = stations;
}

public List<Station> getStations() {
	return stations;
}

public void setStations(List<Station> stations) {
	this.stations = stations;
}

@Override
public String toString() {
	return "StationContainer [stations=" + stations + "]";
}

}
