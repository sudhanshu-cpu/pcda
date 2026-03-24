package com.pcda.util;

public enum RelationType {

	SELF("Self"),
	SPOUSE("Spouse"),
	SON("Son"),
	STEP_SON("Step Son"),
	DAUGHTER("Daughter"),
	STEP_DAUGHTER("Step Daughter"),
	BROTHER("Brother"),
	SISTER("Sister"),
	STEP_MOTHER("Step Mother"),
	STEP_FATHER("Step Father"),
	FATHER("Father"),
	MOTHER("Mother"),
	OTHER("Other"),
	STEP_BROTHER("Step Brother"),
	STEP_SISTER("Step Sister");
	
	private String displayValue;
	
	private RelationType(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}

}
