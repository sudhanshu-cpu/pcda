package com.pcda.mb.reports.userreports.model;

import java.util.List;

import lombok.Data;
@Data
public class UserReportResponse {
	
	
	private String errorMessage;

	private Integer errorCode;

	private GetUserReportModel response;

	private List<GetUserReportModel> responseList;

}
