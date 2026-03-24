package com.pcda.util;

public enum Priority {

	LOW("Low"),
	MEDIUM("Medium"),
	HIGH("High");
	
private final String displayValue;
	
	private Priority(String displayValue) {
		this.displayValue=displayValue;
	}
	
	public String getDisplayValue() {
		return this.displayValue;
	}
}
