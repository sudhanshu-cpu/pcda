package com.pcda.mb.reports.claimreport.model;

import java.util.List;

import lombok.Data;

@Data
public class ClaimReportResponseModel {
	   private String errorMessage;
	   private Integer errorCode;
	   private List<ClaimReportModel> responseList;
}
