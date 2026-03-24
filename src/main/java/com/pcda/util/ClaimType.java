package com.pcda.util;

public enum ClaimType {
	MAIN_CLAIM("Main Claim"),
	SUPPLEMENTARY_CLAIM("Supplementary Claim");
	
	
private final String displayValue;
	
	private ClaimType(String displayValue) {
		this.displayValue=displayValue;
	}
	
	public String getDisplayValue() {
		return this.displayValue;
	}
	
}
