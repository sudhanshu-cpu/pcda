package com.pcda.util;

public enum FromToType {

	FROM("From"),
	TO("To");

private String displayValue;
	
	private FromToType(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
}
