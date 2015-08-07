package com.dto;

import java.util.List;

public class NewRouteSummary {
	private List<String> summary;

	public NewRouteSummary(List<String> summary) {
		super();
		this.summary = summary;
	}

	public List<String> getSummary() {
		return summary;
	}

	public void setSummary(List<String> summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "NewRouteSummary [summary=" + summary + "]";
	}
	
}
