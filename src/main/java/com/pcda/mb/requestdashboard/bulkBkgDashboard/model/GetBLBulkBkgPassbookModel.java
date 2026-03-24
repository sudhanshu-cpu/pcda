package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetBLBulkBkgPassbookModel {
	
	private String finalDestination;
	private String bookingKey;
	private String requestId;
	private List<GetBLBulkBkgFlightDetailLeg> flightDetailLeg;
	private GetBLBulkBkgFlightDetailFare flightDetailFare;
	private GetBLBulkBkgTravellerDetails travellerDetails;

}
