package com.pcda.mb.reports.airrequestreport.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAirReqRepResponse {

	
	private String errorMessage;
	private int errorCode;
	private GetAirReqRepDataModel response;

	private List<GetAirReqRepDataModel> responseList;
}
