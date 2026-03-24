package com.pcda.mb.reports.airticketcancellation.model;

import lombok.Data;

@Data
public class AirTicketBookingDetailsResponse {

	private String errorMessage;
	private Integer errorCode;
	private String request;
	private String requestType;
	private String customMessage;
	
	private AirTicketBookingDetailsResponseModel response;
	private String responseList;
	
	
	
	
}
