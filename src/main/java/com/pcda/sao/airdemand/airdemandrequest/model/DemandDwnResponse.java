package com.pcda.sao.airdemand.airdemandrequest.model;

import lombok.Data;

@Data
public class DemandDwnResponse {

	
	private String errorMessage;
	private int errorCode;
	private String request;
	private String requestType;
	private String customMessage;
	private DemandDwnReqModel response;

	
}
