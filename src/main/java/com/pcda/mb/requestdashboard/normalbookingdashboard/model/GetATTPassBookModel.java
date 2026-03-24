package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetATTPassBookModel {

	private String finalDestination;
	private String bookingKey;
	private String requestId;
	private List<GetAttFlightDetailLeg> flightDetailLeg;
	private GetAttFlightDetailFare flightDetailFare;
	private GetAttTravellerDetails travellerDetails;
}
