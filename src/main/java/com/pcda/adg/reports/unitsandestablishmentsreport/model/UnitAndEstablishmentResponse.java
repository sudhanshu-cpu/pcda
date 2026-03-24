package com.pcda.adg.reports.unitsandestablishmentsreport.model;

import java.util.List;

import lombok.Data;

@Data
public class UnitAndEstablishmentResponse {
	
	private String errorMessage;
	private int errorCode;
	private UnitAndEstablishmentResponseModel response;
	private List<UnitAndEstablishmentResponseModel> responseList ;
}
