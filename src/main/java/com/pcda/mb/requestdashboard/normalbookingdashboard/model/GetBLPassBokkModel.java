package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetBLPassBokkModel {
	
	private String finalDestination;
	private String bookingKey;
	private String requestId;
	private List<GetBLFlightDetailLeg> flightDetailLeg;
	private GetBLFlightDetailFare flightDetailFare;
	private GetBLTravellerDetails travellerDetails;

}
