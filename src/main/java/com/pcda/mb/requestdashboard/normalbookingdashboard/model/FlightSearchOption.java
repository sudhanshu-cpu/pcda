package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class FlightSearchOption implements Serializable{
	
	private static final long serialVersionUID = 7035642341426181659L;
	
	private String serviceProvider;
	private Boolean bookingAllow;
	private Boolean sameFare;
	private String stopOverClass;
	private String multiflight;
	private String intendedTravelFlag;
	private String flightKey;
	private String bookingClassForFare;
	private String fareBaseCode;
	private String filterFareClass;
	private String isRefundable;
	private Double totalFare;
	private String flightLegFlag;
	private String totalJourneyDuration;
	private String overlayoutDuration;
	private String displayFlightName;
	private String displayBookingClass;
	private FlightFareBean flightFareBean;
	private List<FlightLegResponse> flightLegs;

}
