package com.pcda.mb.reports.airtravelreport.model;

import java.util.List;

import lombok.Data;

@Data
public class AirTravelResponse {

	
	private String errorMessage;
	private Integer errorCode;
	private String request;
	private String requestType;
	private String customMessage;
	private List<AirTravelModel> responseList;
}
