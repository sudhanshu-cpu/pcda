package com.pcda.util;

public enum ServiceType {

	ARMED_FORCES("Army"),
	CIVILIAN("Civilian");

	private final String displayValue;

	private ServiceType(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

}
