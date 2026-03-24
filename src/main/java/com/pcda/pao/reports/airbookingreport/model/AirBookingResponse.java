package com.pcda.pao.reports.airbookingreport.model;

import java.util.List;

import lombok.Data;
@Data
public class AirBookingResponse {

	
	private String errorMessage;
	private Integer errorCode;
	private String request;
	private String requestType;
	private String customMessage;
	private List<AirBookingModel> response;
}
