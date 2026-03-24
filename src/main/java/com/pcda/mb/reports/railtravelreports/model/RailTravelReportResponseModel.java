package com.pcda.mb.reports.railtravelreports.model;

import java.util.List;

import lombok.Data;

@Data
public class RailTravelReportResponseModel {
	
	private String errorMessage;
    private int errorCode;
    
    
    private GetRailTravelReportModel response;
    
    private List<GetRailTravelReportModel> responseList;

	
	

}
