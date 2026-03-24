package com.pcda.util;

public enum SectorStation {

	RAIL("Rail"),
	FLIGHT("Flight");
	
	private String displayValue;
	
	private SectorStation(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return this.displayValue;
	}
}
