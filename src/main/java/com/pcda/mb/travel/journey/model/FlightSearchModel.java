package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class FlightSearchModel {

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
	private String jrnyType;

}
