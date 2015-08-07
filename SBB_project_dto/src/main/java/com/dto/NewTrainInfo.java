package com.dto;

public class NewTrainInfo {
	private String trainCapacity;

	public NewTrainInfo(String trainCapacity) {
		super();
		this.trainCapacity = trainCapacity;
	}

	public String getTrainCapacity() {
		return trainCapacity;
	}

	public void setTrainCapacity(String trainCapacity) {
		this.trainCapacity = trainCapacity;
	}

	@Override
	public String toString() {
		return "NewTrainInfo [trainCapacity=" + trainCapacity + "]";
	}
	
}
