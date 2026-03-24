package com.pcda.util;

public enum JourneyDestinationType {

	DUTY_STATION("Duty Station"),
	OLD_DUTY_STATION("Old Duty Station"),
	ANY_PLACE("Any Place"),
	HOME_TOWN("Home Town"),
	SPR("SPR"),
	NEW_DUTY_STATION("New Duty Station"),
	OLD_SPR("Old SPR"),
	NEW_SPR("New SPR"),
	NORTH_EAST("North East"),
	SECTOR("63 Sector"),
	FIXED("Fixed"),
	JAMMU_AND_KASMIR("Jammu & Kashmir"),
	ANDAMAN_AND_NICOBAR("Andaman & Nicobar"),
	RECORD_OFFICE("Record Office"),
	HOSTEL_LOCATION("Hostel Location");
	
	private String displayValue;
	
	private JourneyDestinationType(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}

}
