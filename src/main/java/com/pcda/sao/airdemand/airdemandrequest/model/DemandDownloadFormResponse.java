package com.pcda.sao.airdemand.airdemandrequest.model;

import lombok.Data;

@Data
public class DemandDownloadFormResponse {
	private String errorMessage;
	private String request;
	private int errorCode;
	private String requestType;
	private String customMessage;
	private String response;

}
