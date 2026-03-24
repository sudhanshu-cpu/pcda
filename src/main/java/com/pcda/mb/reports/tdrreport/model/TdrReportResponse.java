package com.pcda.mb.reports.tdrreport.model;

import java.util.List;

import lombok.Data;

@Data
public class TdrReportResponse {

	private String errorMessage;
	private Integer errorCode;
	private String requestType;
	private String customMessage;
	private String  response;
	private List<TdrReportModel> responseList;

}
