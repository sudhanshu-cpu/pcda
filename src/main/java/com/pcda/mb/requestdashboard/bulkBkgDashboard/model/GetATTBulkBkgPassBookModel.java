package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetATTBulkBkgPassBookModel {
	
	private String finalDestination;
	private String bookingKey;
	private String requestId;
	private List<GetATTBulkBkgFlightDetailLeg> flightDetailLeg;
	private GetATTBulkBkgFlightDetailFare flightDetailFare;
	private GetATTBulkBkgTravellerDetails travellerDetails;

}
