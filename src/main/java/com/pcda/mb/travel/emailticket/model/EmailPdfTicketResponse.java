package com.pcda.mb.travel.emailticket.model;

import java.util.List;

import lombok.Data;

@Data
public class EmailPdfTicketResponse {

	
	private String errorMessage;

	private Integer errorCode;

	private AirTicketPdfModel response;

	private List<AirTicketPdfModel> responseList;

	
}
