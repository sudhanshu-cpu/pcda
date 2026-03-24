package com.pcda.util;

public enum AirDestinationType {

	EIGHT(8),
	NINE(9),
	TEN(10),
	ELEVEN(11),
	TWELVE(12);
	
	 private int displayValue;
		
		private AirDestinationType(int value) {
			this.displayValue=value;
		}
		
		public int getDisplayValue() {
			return displayValue;
		}


}
