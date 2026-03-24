package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetBLTravellerDetails {

	private String origin;
	private String destination;
	private String travellingDate;
	private String airline;
	private int adultCount;
	private int childCount;
	private int infantCount;
	private String domint;
	private boolean baggageAllow;
	private List<GetBLPassengerModel> adult;
	private List<GetBLPassengerModel> child;
	private List<GetBLPassengerModel> infant;
}
