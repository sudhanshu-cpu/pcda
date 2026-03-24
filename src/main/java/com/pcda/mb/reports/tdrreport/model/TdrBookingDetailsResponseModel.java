package com.pcda.mb.reports.tdrreport.model;

import lombok.Data;

@Data
public class TdrBookingDetailsResponseModel {
	private String errorMessage;
	private Integer errorCode;
	private String requestType;
	private String customMessage;
	private  TdrBookingPopUpModel response;
	private String responseList;
}
