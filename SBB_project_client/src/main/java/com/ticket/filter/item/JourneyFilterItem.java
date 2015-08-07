package com.ticket.filter.item;

public enum JourneyFilterItem {
	ROUTE("Route name"), 
	DEP_TIME("Departure time"), 
	DEP_ST("Departure station"), 
	ARR_ST("Arrival station"),
	ARR_TIME("Arrival time"),
	TRAIN("Train number");
	
	private String description;

	private JourneyFilterItem(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String toString() {
		return description;
	}
}
