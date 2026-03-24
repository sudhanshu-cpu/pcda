package com.pcda.pao.reports.userverification.model;

import java.util.List;

import lombok.Data;

@Data
public class PaoReportResponse {
	
	private List<PaoReportBean> response;

	private int errorCode;

	private String errorMessage;
	
}

