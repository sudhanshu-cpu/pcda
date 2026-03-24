package com.pcda.adg.reports.claimstatusreport.model;

import java.util.List;

import lombok.Data;

@Data
public class ClaimDataResponse {
	
	private String errorMessage;
	private int errorCode;
	private ClaimInformationResponse response;
	private List<ClaimInformationResponse> responseList;
}
