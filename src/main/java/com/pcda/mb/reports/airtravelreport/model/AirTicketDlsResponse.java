package com.pcda.mb.reports.airtravelreport.model;

import lombok.Data;

@Data
public class AirTicketDlsResponse {
	private String errorMessage;
	private Integer errorCode;
	private String request;
	private String requestType;
	private String customMessage;
	
	private AirTravelBookingResponse response;
	private String responseList;
}
