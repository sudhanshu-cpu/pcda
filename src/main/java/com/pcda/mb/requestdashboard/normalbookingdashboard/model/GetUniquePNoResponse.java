package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetUniquePNoResponse {

	private String errorMessage;
	private int errorCode;
	private List<String> response;
	private String responseList;
	
}
