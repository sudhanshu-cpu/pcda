package com.pcda.util;

public enum Department {

	NOT_ASSIGNED("Not Assigned"),
	RAIL_HELPLINE("Rail Helpline"),
	AIR_HELPLINE("Air Helpline"),
	ADMIN("Admin"),
	TECHNICAL("Technical Team");
	
private final String displayValue;
	
	private Department(String displayValue) {
		this.displayValue=displayValue;
	}
	
	public String getDisplayValue() {
		return this.displayValue;
	}
}
