package com.pcda.pao.reports.airdemandreport.model;

import java.util.List;

import lombok.Data;

@Data
public class PaoAirDmdStatusReportResponse {
	
	private String errorMessage;
	private int errorCode;
	private PaoAirDmdStReportModel response;
	private List<PaoAirDmdStReportModel> responseList;

}
