package com.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Seats
 *
 */
@Entity

public class Seats implements Serializable {
@Id
@GeneratedValue (strategy=GenerationType.AUTO)
@Column(name = "seats_id")
private int seatsId;

@ManyToOne
@JoinColumn(name = "journey_id", referencedColumnName = "journey_id")
private Journey journey;

@Column(name = "route_step")
private int routeStep;

@Column(name = "empty_seats")
private int emptySeats;





public Seats(int seatsId, Journey journey, int routeStep, int emptySeats) {
	super();
	this.seatsId = seatsId;
	this.journey = journey;
	this.routeStep = routeStep;
	this.emptySeats = emptySeats;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + seatsId;
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
	Seats other = (Seats) obj;
	if (seatsId != other.seatsId)
		return false;
	return true;
}





	public int getSeatsId() {
	return seatsId;
}

public void setSeatsId(int seatsId) {
	this.seatsId = seatsId;
}

public Journey getJourney() {
	return journey;
}

public void setJourney(Journey journey) {
	this.journey = journey;
}

public int getRouteStep() {
	return routeStep;
}

public void setRouteStep(int routeStep) {
	this.routeStep = routeStep;
}

public int getEmptySeats() {
	return emptySeats;
}

public void setEmptySeats(int emptySeats) {
	this.emptySeats = emptySeats;
}





	private static final long serialVersionUID = 1L;

	public Seats() {
		super();
	}
   
}
