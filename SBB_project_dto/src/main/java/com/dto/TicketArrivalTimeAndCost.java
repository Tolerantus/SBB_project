package com.dto;

import java.util.Date;

import com.entities.Ticket;

public class TicketArrivalTimeAndCost {
	private Ticket ticket;
	private Date arrivalTime;
	private double cost;
	
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public TicketArrivalTimeAndCost() {
		super();
	}
	public TicketArrivalTimeAndCost(Ticket ticket, Date arrivalTime, double cost) {
		super();
		this.ticket = ticket;
		this.arrivalTime = arrivalTime;
		this.cost = cost;
	}
	
	
}
