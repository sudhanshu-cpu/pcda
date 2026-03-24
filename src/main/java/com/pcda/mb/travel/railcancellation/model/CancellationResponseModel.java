package com.pcda.mb.travel.railcancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class CancellationResponseModel {

	private String errorMessage;

	private int errorCode;

	private GetCancellationPostData response;

	private List<GetCancellationPostData> responseList;
	
	
	
}
