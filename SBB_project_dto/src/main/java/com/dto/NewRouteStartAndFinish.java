package com.dto;



public class NewRouteStartAndFinish {
	private String typedDep;
	private String typedArr;
	private String selectDep; 
	private String selectArr;
	public NewRouteStartAndFinish(String typedDep, String typedArr,
			String selectDep, String selectArr) {
		super();
		this.typedDep = typedDep;
		this.typedArr = typedArr;
		this.selectDep = selectDep;
		this.selectArr = selectArr;
	}
	public String getTypedDep() {
		return typedDep;
	}
	public void setTypedDep(String typedDep) {
		this.typedDep = typedDep;
	}
	public String getTypedArr() {
		return typedArr;
	}
	public void setTypedArr(String typedArr) {
		this.typedArr = typedArr;
	}
	public String getSelectDep() {
		return selectDep;
	}
	public void setSelectDep(String selectDep) {
		this.selectDep = selectDep;
	}
	public String getSelectArr() {
		return selectArr;
	}
	public void setSelectArr(String selectArr) {
		this.selectArr = selectArr;
	}
	
	
	
	
	
}
