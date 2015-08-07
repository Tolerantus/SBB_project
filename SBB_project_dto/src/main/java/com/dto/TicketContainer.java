package com.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.entities.UserAndTicket;
@XmlRootElement
public class TicketContainer {
	
	private List<UserAndTicket> tickets;
	private TicketListConstraints constraints;
	private List<TicketArrivalTimeAndCost> ticketArrivalTimeAndCost;
	private String startDate;
	private String stopDate;
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStopDate() {
		return stopDate;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

	public List<UserAndTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<UserAndTicket> tickets) {
		this.tickets = tickets;
	}

	public TicketListConstraints getConstraints() {
		return constraints;
	}

	public void setConstraints(TicketListConstraints constraints) {
		this.constraints = constraints;
	}

	public List<TicketArrivalTimeAndCost> getTicketArrivalTimeAndCost() {
		return ticketArrivalTimeAndCost;
	}

	public void setTicketArrivalTimeAndCost(
			List<TicketArrivalTimeAndCost> ticketArrivalTimeAndCost) {
		this.ticketArrivalTimeAndCost = ticketArrivalTimeAndCost;
	}

	
	
}
