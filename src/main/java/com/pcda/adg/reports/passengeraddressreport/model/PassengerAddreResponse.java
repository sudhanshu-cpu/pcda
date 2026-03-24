package com.pcda.adg.reports.passengeraddressreport.model;

import lombok.Data;

@Data
public class PassengerAddreResponse {

	private String errorMessage;
	private Integer errorCode;
	private GetPassengerAddresModel response;
	private GetPassengerAddresModel responseList;

}
