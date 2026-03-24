package com.pcda.co.requestdashboard.approvebooking.model;

import java.util.List;

import lombok.Data;

@Data
public class GetDADetailsResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetAppBookDADetails> responseList;
	private GetAppBookDADetails response;
}
