package com.pcda.util;

public enum Gender {

	MALE("Male"),
	FEMALE("Female");
	
private String displayValue;
	
	private Gender(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
}
