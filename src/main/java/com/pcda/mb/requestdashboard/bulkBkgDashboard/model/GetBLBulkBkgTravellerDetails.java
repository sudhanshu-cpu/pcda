package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetBLBulkBkgTravellerDetails {
	
	private String origin;
	private String destination;
	private String travellingDate;
	private String airline;
	private int adultCount;
	private int childCount;
	private int infantCount;
	private String domint;
	private boolean baggageAllow;
	private List<GetBLBulkBkgPassengerModel> adult;
	private List<GetBLBulkBkgPassengerModel> child;
	private List<GetBLBulkBkgPassengerModel> infant;

}
