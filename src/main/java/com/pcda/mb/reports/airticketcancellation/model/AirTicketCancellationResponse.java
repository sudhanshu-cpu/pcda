package com.pcda.mb.reports.airticketcancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class AirTicketCancellationResponse {
	
	private String errorMessage;
	private Integer errorCode;
	private String request;
	private String requestType;
	private String customMessage;
	private String response;
	
	private List<AirTicketCancellationModel>  responseList;
	
	
	
}
