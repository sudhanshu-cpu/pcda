package com.pcda.util;

public enum DODUserType {

	SITE_USER("Site User"),
	TRAVELER("Traveler");
	
	private String displayValue;
	
	private DODUserType(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
}
