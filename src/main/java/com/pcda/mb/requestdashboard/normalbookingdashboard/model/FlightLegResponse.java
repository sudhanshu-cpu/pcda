package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlightLegResponse implements Serializable{
	
	private static final long serialVersionUID = 153969277862849304L;
	
	private String arrivalDate;
	private String displayArrivalDate;
	private String arrivalTime;
	private String arrivalTerminal;
	private String departureDate;
	private String displayDepartureDate;
	private String departureTime;
	private String filterTimeSlot;
	private String departureTerminal;
	private String carrier;
	private String isConnectedCarrier;
	private String carrierName;
	private String carrierImageClass;
	private String destination;
	private String destinationName;
	private String origin;
	private String originName;
	private String journeyDuration;
	private String stopover;
	private String stopoverInfo;
	private String bookingClass;
	private String cabinClass;
	private String equipment;
	private String flightNumber;
	private String numSeatAvailable;
	private String onTimeInfo;
	private String validatingCarrier;
	private String validatingCarrierName;
	private String weatherDestination;
	private String weatherOrigin;

}
