package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class FlightSearchOption {

	private String serviceProvider;
	private boolean bookingAllow;
	private boolean sameFare;
	private String stopOverClass;
	private String multiflight;
	private String intendedTravelFlag;
	private String flightKey;
	private String bookingClassForFare;
	private String fareBaseCode;
	private String filterFareClass;
	private String isRefundable;
	private double totalFare;
	private String flightLegFlag;
	private String totalJourneyDuration;
	private String overlayoutDuration;
	private String displayFlightName;
	private String displayBookingClass;
	private FlightFareBean flightFareBean;
	private List<FlightLegResponse> flightLegs;

}
