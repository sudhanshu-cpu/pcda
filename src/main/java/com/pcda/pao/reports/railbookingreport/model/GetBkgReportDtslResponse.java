package com.pcda.pao.reports.railbookingreport.model;

import java.util.List;

import lombok.Data;

@Data
public class GetBkgReportDtslResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetBkgReportDtlsModel> responseList;
	private GetBkgReportDtlsModel response;
}
