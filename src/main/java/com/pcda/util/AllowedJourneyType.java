package com.pcda.util;

public enum AllowedJourneyType {

	ONWARD("Onward"),
	RETURN("Return"),
	BOTH("Both"),
	ADDITIONAL_WARRANT("Additional Warrant");
	
private String displayValue;
	
	private AllowedJourneyType(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}

}
