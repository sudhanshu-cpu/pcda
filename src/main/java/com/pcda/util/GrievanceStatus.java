package com.pcda.util;

public enum GrievanceStatus {

	REQUEST_RECIEVED("Submitted"),
	REQUEST_ASSIGNED("In process"),
	REQUEST_RESOLVED("Resolved"),
	EXTRA_INFO_REQUIRED("Extra info required"),
	EXTRA_INFO_SUBMITTED("Extra info submitted"),
	AUTO_CLOSED("Auto Closed");
	
	private final String displayValue;
	
	private GrievanceStatus(String displayValue) {
		this.displayValue=displayValue;
	}
	
	public String getDisplayValue() {
		return this.displayValue;
	}
}
