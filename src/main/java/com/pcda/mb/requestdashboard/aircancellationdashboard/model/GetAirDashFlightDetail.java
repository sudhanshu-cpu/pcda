package com.pcda.mb.requestdashboard.aircancellationdashboard.model;

import lombok.Data;

@Data 
public class GetAirDashFlightDetail {
	private int flightSegementNo;
	private int flightSectorNo;
	private String spPNRNo;
	private String airlinePNRNo;
	private String segementOrigin;
	private String segementDestination;
	private String airline;
	private String flightNo;
	private String departureTime;
	private String arrivalTime;
	private String departureTerminal;
	private String arrivalTerminal;
	private String baggageAllowance;
	private String operatingAirline;
	private String mealCode;
	private String craftType;
	private Double mealAmount;
	private String cabinClass;
	private String fareClass;
	private String cancellationAllowed;

}
