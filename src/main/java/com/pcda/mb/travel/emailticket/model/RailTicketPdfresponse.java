package com.pcda.mb.travel.emailticket.model;

import java.util.List;

import lombok.Data;

@Data
public class RailTicketPdfresponse {

	private String errorMessage;

	private Integer errorCode;

	private RailTicketPdfModel response;

	private List<RailTicketPdfModel> responseList;

	
}
