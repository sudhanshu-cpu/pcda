package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetBLFlightDetailLeg {

	
	private String arrivalDate;
	private String arrivalTime;
	private String arrivalTerminal;
	private String departureDate;
	private String departureTime;
	private String departureTerminal;
	private String destination;
	private String destinationName;
	private String origin;
	private String originName;
	private String journeyDuration;
	private String bookingClass;
	private String cabinClass;
	private String flightNumber;
	private String carrierName;
	private String carrier;
	private String carrierImageClass;
}
