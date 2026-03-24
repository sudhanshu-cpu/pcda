package com.pcda.util;

public enum YesOrNo {
	YES("Yes"),
	NO("No");

	private String displayValue;
	
	private YesOrNo(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
}
