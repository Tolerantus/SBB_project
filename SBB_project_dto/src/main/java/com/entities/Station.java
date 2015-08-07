package com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




/**
 * Entity implementation class for Entity: Station
 *
 */
@Entity
public class Station implements Serializable {
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column(name = "station_id")
	private int stationId;
	@Column(name = "station_name")
	private String stationName;	

	private static final long serialVersionUID = 1L;

	public Station(int stationId, String stationName) {
		super();
		this.stationId = stationId;
		this.stationName = stationName;
	}

	public Station() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + stationId;
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
		Station other = (Station) obj;
		if (stationId != other.stationId)
			return false;
		return true;
	}

	

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	@Override
	public String toString() {
		return stationName;
	}
   
}
