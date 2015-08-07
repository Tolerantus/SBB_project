package com.dto;

import java.util.List;

public class PassengerInfo {
	private String currentUser;
	private String passengerDepAndDestStations;
	private String name;
	private String surname;
	private String year;
	private String month;
	private String day;
	private List<String> allJourneysData;
	public PassengerInfo(String currentUser,
			String passengerDepAndDestStations, String name, String surname,
			String year, String month, String day, List<String> allJourneysData) {
		super();
		this.currentUser = currentUser;
		this.passengerDepAndDestStations = passengerDepAndDestStations;
		this.name = name;
		this.surname = surname;
		this.year = year;
		this.month = month;
		this.day = day;
		this.allJourneysData = allJourneysData;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
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
				+ surname + ", year=" + year + ", month=" + month + ", day="
				+ day + ", allJourneysData=" + allJourneysData + "]";
	}
	
	
}
