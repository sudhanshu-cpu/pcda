package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetFinalBookRailTktResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetFinalTicketBookDtls> responseList;
	private GetFinalTicketBookDtls response ;
}
