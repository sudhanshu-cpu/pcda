package com.pcda.mb.reports.airticketcancellation.model;

import lombok.Data;

@Data
public class FlightDetailsModel {

	
	private Integer flightSegmentNo;
	private Integer flightSectorNo;
	private String spPnrNo;
	private String airlinePnrNo;
	private String segmentOrigin;
	private String segmentDestination;
	private String airline;
	private String flightNo;
	private String departureTime;
	private String arrivalTime;
	private String deptTerminal;
	private String arrTerminal;
	private String baggageAllowance;
	private String opeartingAirline;
	private String mealCode;
	private String craftType;
	private Integer mealAmount;
	private String cabinClass;
	private String fareClass;
	
		
	
}
