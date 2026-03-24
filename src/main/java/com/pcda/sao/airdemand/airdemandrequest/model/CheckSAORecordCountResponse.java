package com.pcda.sao.airdemand.airdemandrequest.model;

import java.util.List;

import lombok.Data;
@Data
public class CheckSAORecordCountResponse {

	private String errorMessage;
	private Integer errorCode;
	private String request;
	private String requestType;
	private String customMessage;
	private List<DemandDownloadRequestModel> responseList;
	private long response;
	
	
	
	
	
	
	
}
