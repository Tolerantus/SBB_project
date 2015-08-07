package com.dto;

public class StationsForSheduling {
private String singleStation;
private String stDep;
private String stArr;
private String date;
public StationsForSheduling(String singleStation, String stDep, String stArr,
		String date) {
	super();
	this.singleStation = singleStation;
	this.stDep = stDep;
	this.stArr = stArr;
	this.date = date;
}
public String getSingleStation() {
	return singleStation;
}
public void setSingleStation(String singleStation) {
	this.singleStation = singleStation;
}
public String getStDep() {
	return stDep;
}
public void setStDep(String stDep) {
	this.stDep = stDep;
}
public String getStArr() {
	return stArr;
}
public void setStArr(String stArr) {
	this.stArr = stArr;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}




}
