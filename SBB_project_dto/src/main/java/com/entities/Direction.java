package com.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Direction
 *
 */
@Entity
public class Direction implements Serializable {
@Id
@GeneratedValue (strategy=GenerationType.AUTO)
@Column(name = "direction_id")
private int directionId;

@ManyToOne
@JoinColumn(name = "st_dep", referencedColumnName = "station_id")
private Station stDep;

@ManyToOne
@JoinColumn(name = "st_arr", referencedColumnName = "station_id")
private Station stArr;
private long time;
private double cost;




@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + directionId;
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
	Direction other = (Direction) obj;
	if (directionId != other.directionId)
		return false;
	return true;
}




	public Direction(int directionId, Station stDep, Station stArr, long time,
		double cost) {
	super();
	this.directionId = directionId;
	this.stDep = stDep;
	this.stArr = stArr;
	this.time = time;
	this.cost = cost;
}




	public int getDirectionId() {
		return directionId;
	}

	public void setDirectionId(int directionId) {
		this.directionId = directionId;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}




	private static final long serialVersionUID = 1L;

public Direction() {
	super();
}

@Override
public String toString() {
	return stDep + " - " + stArr;
}
   
}
