package com.pcda.co.requestdashboard.approveaircancellation.model;

import lombok.Data;

@Data
public class FlightDetail {
	
	private Integer flightSegementNo;
	private Integer flightSectorNo;
	
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
	private Integer mealAmount;
	
	private String cabinClass;
	private String fareClass;

}

