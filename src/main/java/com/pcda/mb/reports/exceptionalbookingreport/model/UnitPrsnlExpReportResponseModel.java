package com.pcda.mb.reports.exceptionalbookingreport.model;

import java.util.List;

import lombok.Data;

@Data
public class UnitPrsnlExpReportResponseModel {

	private String errorMessage;
    private int errorCode;
    
    
    private GetUnitPrsnlExpReportData response;
    
    private List<GetUnitPrsnlExpReportData> responseList;
}
