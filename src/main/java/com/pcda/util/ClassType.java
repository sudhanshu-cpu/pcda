package com.pcda.util;

public enum ClassType {

	ANUBHUTI_CLASS_EA("Anubhuti Class (EA)"),
	AC_FIRST_CLASS_1A("AC First Class (1A)"), 
	VISTADOME_AC_EV("Vistadome AC (EV)"),
	EXEC_CHAIR_CAR_EC("Exec. Chair Car (EC)"),
	AC_2_TIER_2A("AC 2 Tier (2A)"),
	FIRST_CLASS_FC("First Class (FC)"),
	AC_3_TIER_3A("AC 3 Tier (3A)"),
	AC_3_ECONOMY_3E("AC 3 Economy (3E)"),
	VISTADOME_CHAIR_CAR_VC("Vistadome Chair Car (VC)"),
	AC_CHAIR_CAR_CC("AC Chair car (CC)"),
	SLEEPER_SL("Sleeper (SL)"),
	VISTADOME_NON_AC_VS("Vistadome Non AC (VS)"),
	SECOND_SITTING_2S("Second Sitting (2S)");
	
	private final String displayValue;
	
	private ClassType(String displayValue) {
		this.displayValue=displayValue;
	}
	
	public String getDisplayValue() {
		return this.displayValue;
	}
	
}
