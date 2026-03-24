package com.pcda.mb.reports.daadvancereport.model;

import java.util.List;

import lombok.Data;

@Data
public class DaAdvanceReportModelResponse {
	
	  private String errorMessage;
	   private Integer errorCode;
	   private List<GetDaAdvanceModel> responseList;


}
