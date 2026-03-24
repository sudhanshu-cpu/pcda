package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class PostAirBookBlModel {
	private String requestId ;
	private String flightKey ;
	private String  domint;
	private FlightSearchOption flightOption;
}
