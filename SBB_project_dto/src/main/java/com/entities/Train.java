package com.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Train
 *
 */
@Entity

public class Train implements Serializable {
@Id 
@GeneratedValue(strategy=GenerationType.AUTO)
@Column(name = "train_id")
private int trainId;

@Column(name = "train_seats")
private int trainSeats;
	
	@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + trainId;
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
	Train other = (Train) obj;
	if (trainId != other.trainId)
		return false;
	return true;
}

	private static final long serialVersionUID = 1L;

	public Train() {
		super();
	}

	public Train(int trainId, int trainSeats) {
		super();
		this.trainId = trainId;
		this.trainSeats = trainSeats;
	}

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}

	public int getTrainSeats() {
		return trainSeats;
	}

	public void setTrainSeats(int trainSeats) {
		this.trainSeats = trainSeats;
	}


   
}
