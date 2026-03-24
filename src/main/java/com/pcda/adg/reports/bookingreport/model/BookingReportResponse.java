package com.pcda.adg.reports.bookingreport.model;

import java.util.List;

import lombok.Data;

@Data
public class BookingReportResponse {

	private String errorMessage;
	
	private Integer errorCode;
	
	private String request;
	private String requestType;
	private String customMessage;
	
	private GetBookingRepoModel response;
	private List<GetBookingRepoModel> responseList;

	
	

	
}
