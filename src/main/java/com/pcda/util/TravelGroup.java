package com.pcda.util;

public enum TravelGroup {

	CONFERENCE_MEETING("Conference/Meeting"),
	LEGAL_COURT("Legal/Court"),
	MEDICAL("Medical"),
	NOT_SPECIFIED("Not Specified"),
	OTHERS("Others"),
	SPORTS("Sports"),
	TRAINING_EXAMINATION("Training/Examination"),
	TY_DUTY("Ty Duty"),
	PARTY_GROUP("Party Group"),
	TA_RETIREMENT("TA-Retirement"),
	DOG_HANDLERS("Dog Handlers"),
	TA_DEATH("TA-Death");
	
	private String displayValue;
	
	private TravelGroup(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}

}
