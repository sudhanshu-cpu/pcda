package com.pcda.util;

public enum Status {

	OFF_LINE("Off-line"),
	ON_LINE("On-line"); 

	private String displayValue;
	
	private Status(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
}
