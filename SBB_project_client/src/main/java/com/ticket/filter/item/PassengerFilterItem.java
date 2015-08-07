package com.ticket.filter.item;

public enum PassengerFilterItem {
	NAME("Name"), 
	SURNAME("Surname"), 
	BIRTHDAY("Birthday");
	
	private String description;

	private PassengerFilterItem(String description) {
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
