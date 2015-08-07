package com.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;




/**
 * Entity implementation class for Entity: Journey
 *
 */
@Entity

public class Journey implements Serializable {
@Id
@GeneratedValue (strategy=GenerationType.AUTO)
@Column(name = "journey_id")
private int journeyId;

@ManyToOne
@JoinColumn(name = "route_id", referencedColumnName = "route_id")
private Route route;

@ManyToOne
@JoinColumn(name = "train_id", referencedColumnName = "train_id")
private Train train;

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "time_dep")
private Date timeDep;
	
	

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + journeyId;
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
	Journey other = (Journey) obj;
	if (journeyId != other.journeyId)
		return false;
	return true;
}

public int getJourneyId() {
	return journeyId;
}



public void setJourneyId(int journeyId) {
	this.journeyId = journeyId;
}



public Route getRoute() {
	return route;
}



public void setRoute(Route route) {
	this.route = route;
}



public Train getTrain() {
	return train;
}



public void setTrain(Train train) {
	this.train = train;
}



public Date getTimeDep() {
	return timeDep;
}



public void setTimeDep(Date timeDep) {
	this.timeDep = timeDep;
}



	public Journey(int journeyId, Route route, Train train, Date timeDep) {
	super();
	this.journeyId = journeyId;
	this.route = route;
	this.train = train;
	this.timeDep = timeDep;
}






	private static final long serialVersionUID = 1L;

	public Journey() {
		super();
	}
   
}
