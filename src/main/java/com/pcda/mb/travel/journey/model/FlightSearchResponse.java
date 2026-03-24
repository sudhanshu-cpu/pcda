package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class FlightSearchResponse {

	private int errorCode;
	private String errorMessage;
	private FlightSearchModel response;

}
