package com.pcda.util;

public enum CabinClass {

	BUSINESS_CLASS("Business Class"),
	PREMIUM_ECONOMY_CLASS("Premium Economy Class"),
	ECONOMY_CLASS("Economy Class");
	
	private final String displayValue;
	
	private CabinClass(String displayValue) {
		this.displayValue=displayValue;
	}
	
	public String getDisplayValue() {
		return this.displayValue;
	}
}
