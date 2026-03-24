package com.pcda.mb.reports.exceptionalbookingreport.model;

import java.util.List;

import lombok.Data;

@Data
public class ExcepBkgDetailsResponseModel {
	
	private String errorMessage;
    private int errorCode;
    
    
    private List<GetExcepBkgDetailsModel> responseList;
    
    private GetExcepBkgDetailsModel response;
}
