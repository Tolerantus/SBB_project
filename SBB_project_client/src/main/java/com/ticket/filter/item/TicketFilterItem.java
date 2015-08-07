package com.ticket.filter.item;

public enum TicketFilterItem {
	NUMBER("Ticket number"), 
	COST("Cost"), 
	PURCH_DATE("Purchase date"), 
	USER("User e-mail");
	
	private String description;
	
	private TicketFilterItem(String description) {
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
