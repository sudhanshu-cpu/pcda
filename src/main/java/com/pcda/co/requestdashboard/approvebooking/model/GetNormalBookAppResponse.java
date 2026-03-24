package com.pcda.co.requestdashboard.approvebooking.model;

import java.util.List;

import lombok.Data;

@Data
public class GetNormalBookAppResponse {

	private String errorMessage;
	private int errorCode;
	private List<String> responseList;
	private String response;
}
