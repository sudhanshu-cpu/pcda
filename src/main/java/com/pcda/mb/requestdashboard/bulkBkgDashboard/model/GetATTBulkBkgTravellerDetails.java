package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetATTBulkBkgTravellerDetails {
	private String origin;
	private String destination;
	private String travellingDate;
	private String airline;
	private int adultCount;
	private int childCount;
	private int infantCount;
	private String domint;
	private boolean baggageAllow;
	private List<GetATTBulkBkgPassangetDetails> adult;
	private List<GetATTBulkBkgPassangetDetails> child;
	private List<GetATTBulkBkgPassangetDetails> infant;


}
