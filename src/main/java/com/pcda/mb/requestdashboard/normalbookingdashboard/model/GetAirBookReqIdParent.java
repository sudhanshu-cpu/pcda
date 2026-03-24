package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAirBookReqIdParent {

	private String onwardJourneyDate;
	private String travelClass;
	private int aduldCount;
	private int childCount;
	private int infantCount;
	private String travellerOrigin;
	private String travellerDestination;
    private String searchType;
	private List<FlightSearchOption> flightOption;
	private List<Carrier> carriers;
	private int requestMode;
	private String topFlightKey;
	private String reqTimeSlot;
	private Integer bookingType;
	

}
