package com.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * Entity implementation class for Entity: Ticket
 *
 */
@Entity

public class Ticket implements Serializable {
@Id
@GeneratedValue (strategy=GenerationType.AUTO)
@Column(name = "ticket_id")
private int ticketId;

@ManyToOne
@JoinColumn(name = "passenger_id", referencedColumnName = "passenger_id")
private Passenger passenger;

@ManyToOne
@JoinColumn(name = "journey_id", referencedColumnName = "journey_id")
private Journey journey;

@ManyToOne
@JoinColumn(name = "st_dep", referencedColumnName = "station_id")
private Station stDep;


@ManyToOne
@JoinColumn(name = "st_arr", referencedColumnName = "station_id")
private Station stArr;

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "purchase_date")
private Date purchaseDate;	

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ticketId;
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Ticket other = (Ticket) obj;
	if (ticketId != other.ticketId)
		return false;
	return true;
}

public int getTicketId() {
	return ticketId;
}

public void setTicketId(int ticketId) {
	this.ticketId = ticketId;
}

public Passenger getPassenger() {
	return passenger;
}

public void setPassenger(Passenger passenger) {
	this.passenger = passenger;
}

public Journey getJourney() {
	return journey;
}

public void setJourney(Journey journey) {
	this.journey = journey;
}

public Station getStDep() {
	return stDep;
}

public void setStDep(Station stDep) {
	this.stDep = stDep;
}

public Station getStArr() {
	return stArr;
}

public void setStArr(Station stArr) {
	this.stArr = stArr;
}

public Date getPurchaseDate() {
	return purchaseDate;
}

public void setPurchaseDate(Date purchaseDate) {
	this.purchaseDate = purchaseDate;
}

	public Ticket(int ticketId, Passenger passenger, Journey journey,
		Station stDep, Station stArr, Date purchaseDate) {
	super();
	this.ticketId = ticketId;
	this.passenger = passenger;
	this.journey = journey;
	this.stDep = stDep;
	this.stArr = stArr;
	this.purchaseDate = purchaseDate;
}

	private static final long serialVersionUID = 1L;

	public Ticket() {
		super();
	}

	
   
}
