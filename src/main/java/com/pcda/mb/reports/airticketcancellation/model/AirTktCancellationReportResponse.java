package com.pcda.mb.reports.airticketcancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class AirTktCancellationReportResponse {

	private String errorMessage;
    private int errorCode;
    
    private AirTktCancellationDataModel response;
    
    private List<AirTktCancellationDataModel> responseList;
}
