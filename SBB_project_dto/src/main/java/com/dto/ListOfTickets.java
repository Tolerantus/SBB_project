package com.dto;

import java.util.List;

public class ListOfTickets {
	List<String> ticketsInfo;

	public ListOfTickets(List<String> ticketsInfo) {
		super();
		this.ticketsInfo = ticketsInfo;
	}

	public List<String> getTicketsInfo() {
		return ticketsInfo;
	}

	public void setTicketsInfo(List<String> ticketsInfo) {
		this.ticketsInfo = ticketsInfo;
	}

	@Override
	public String toString() {
		return "ListOfTickets [ticketsInfo=" + ticketsInfo + "]";
	}
	
}
