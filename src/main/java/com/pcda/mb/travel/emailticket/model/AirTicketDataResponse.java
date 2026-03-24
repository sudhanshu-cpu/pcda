package com.pcda.mb.travel.emailticket.model;

import java.util.List;

import lombok.Data;

@Data
public class AirTicketDataResponse {

	
	
	private String errorMessage;

	private Integer errorCode;

	private AirTicketAllData response;

	private List<AirTicketAllData> responseList;
}
