package com.dto;

import java.util.List;

public class PassengerInfo {
	private String currentUser;
	private String passengerDepAndDestStations;
	private String name;
	private String surname;
	private String birthday;
	private List<String> allJourneysData;
	
	public PassengerInfo(String currentUser,
			String passengerDepAndDestStations, String name, String surname,
			String birthday, List<String> allJourneysData) {
		super();
		this.currentUser = currentUser;
		this.passengerDepAndDestStations = passengerDepAndDestStations;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.allJourneysData = allJourneysData;
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public String getPassengerDepAndDestStations() {
		return passengerDepAndDestStations;
	}
	public void setPassengerDepAndDestStations(String passengerDepAndDestStations) {
		this.passengerDepAndDestStations = passengerDepAndDestStations;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public List<String> getAllJourneysData() {
		return allJourneysData;
	}
	public void setAllJourneysData(List<String> allJourneysData) {
		this.allJourneysData = allJourneysData;
	}

	@Override
	public String toString() {
		return "PassengerInfo [currentUser=" + currentUser
				+ ", passengerDepAndDestStations="
				+ passengerDepAndDestStations + ", name=" + name + ", surname="
				+ surname + ", birthday=" + birthday + "]";
	}
	
	
	
}
