package com.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Shedule
 *
 */
@Entity
public class Shedule implements Serializable {
	
@Id
@GeneratedValue (strategy=GenerationType.AUTO)
@Column(name = "shedule_id")
private int sheduleId;

@ManyToOne
@JoinColumn(name = "direction_id", referencedColumnName = "direction_id")
private Direction direction;

@ManyToOne
@JoinColumn(name = "route_id", referencedColumnName = "route_id")
private Route route;

@Column(name = "step")
private int step;
	




	public Shedule(int sheduleId, Direction direction, Route route, int step) {
	super();
	this.sheduleId = sheduleId;
	this.direction = direction;
	this.route = route;
	this.step = step;
}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sheduleId;
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
		Shedule other = (Shedule) obj;
		if (sheduleId != other.sheduleId)
			return false;
		return true;
	}



	


	public int getSheduleId() {
		return sheduleId;
	}

	public void setSheduleId(int sheduleId) {
		this.sheduleId = sheduleId;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}






	private static final long serialVersionUID = 1L;

	public Shedule() {
		super();
	}
   
}
