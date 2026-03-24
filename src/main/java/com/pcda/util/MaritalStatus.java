package com.pcda.util;

public enum MaritalStatus {

	MARRIED("Married"),
	UNMARRIED("Unmarried"),
	DIVORCED("Divorced"),
	ABANDONED("Abandonment"),
	WIDOW("Widow");
	
	private String displayValue;
	
	private MaritalStatus(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}

}
