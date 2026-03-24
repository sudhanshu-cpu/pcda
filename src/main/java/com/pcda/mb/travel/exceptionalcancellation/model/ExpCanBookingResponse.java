package com.pcda.mb.travel.exceptionalcancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class ExpCanBookingResponse {

	private String errorMessage;

	private int errorCode;

	private GetExpCancelParentModel response;

	private List<GetExpCancelParentModel> responseList;
	
}
