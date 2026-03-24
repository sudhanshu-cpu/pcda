package com.pcda.mb.reports.exceptionalbookingreport.model;

import java.util.List;

import lombok.Data;

@Data
public class ExceptionalRepostResponseModel {
	private String errorMessage;
    private int errorCode;
    
    
    private GetExceptionalReportData response;
    
    private List<GetExceptionalReportData> responseList;
}
