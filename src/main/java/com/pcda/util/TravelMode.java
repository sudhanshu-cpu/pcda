package com.pcda.util;

public enum TravelMode {

	RAIL("Rail"), 
	AIR("Air"),  
	MIXED("Mixed");
	
	private String displayValue;
	
	private TravelMode(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}

	
	
}
