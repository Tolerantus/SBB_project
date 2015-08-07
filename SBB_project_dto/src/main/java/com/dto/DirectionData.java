package com.dto;

import java.util.List;

public class DirectionData {
	private List<String> directions;

	public DirectionData(List<String> directions) {
		super();
		this.directions = directions;
	}

	public List<String> getDirections() {
		return directions;
	}

	public void setDirections(List<String> directions) {
		this.directions = directions;
	}

	@Override
	public String toString() {
		return "DirectionData [directions=" + directions + "]";
	}
	
}
