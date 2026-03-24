package com.pcda.mb.travel.aircancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class AirCancellationResponse {

	
	
	private String errorMessage;

	private int errorCode;

	private AirCancellationModel response;

	private List<AirCancellationModel> responseList;
	
}
