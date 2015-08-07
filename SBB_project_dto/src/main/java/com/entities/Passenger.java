package com.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Passenger
 *
 */
@Entity

public class Passenger implements Serializable {
@Id
@GeneratedValue (strategy=GenerationType.AUTO)
@Column(name = "passenger_id")
private int passengerId;

@Column(name = "passenger_name")
private String passengerName;

@Column(name = "passenger_surname")
private String passengerSurname;

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "passenger_birthday")
private Date passengerBirthday;

private static final long serialVersionUID = 1L;
public Passenger(int passenger_id, String passenger_name) {
	super();
	this.passengerId = passenger_id;
	this.passengerName = passenger_name;
}

	@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime
			* result
			+ ((passengerBirthday == null) ? 0 : passengerBirthday.hashCode());
	result = prime * result
			+ ((passengerName == null) ? 0 : passengerName.hashCode());
	result = prime * result
			+ ((passengerSurname == null) ? 0 : passengerSurname.hashCode());
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
	Passenger other = (Passenger) obj;
	if (passengerBirthday == null) {
		if (other.passengerBirthday != null)
			return false;
	} else if (!passengerBirthday.equals(other.passengerBirthday))
		return false;
	if (passengerName == null) {
		if (other.passengerName != null)
			return false;
	} else if (!passengerName.equals(other.passengerName))
		return false;
	if (passengerSurname == null) {
		if (other.passengerSurname != null)
			return false;
	} else if (!passengerSurname.equals(other.passengerSurname))
		return false;
	return true;
}

	public Passenger() {
		super();
	}

	public int getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getPassengerSurname() {
		return passengerSurname;
	}

	public void setPassengerSurname(String passengerSurname) {
		this.passengerSurname = passengerSurname;
	}

	public Date getPassengerBirthday() {
		return passengerBirthday;
	}

	public void setPassengerBirthday(Date passengerBirthday) {
		this.passengerBirthday = passengerBirthday;
	}

	
   
}
