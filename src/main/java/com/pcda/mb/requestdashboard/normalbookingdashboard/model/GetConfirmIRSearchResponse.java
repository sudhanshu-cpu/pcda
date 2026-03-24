package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetConfirmIRSearchResponse {
	private String errorMessage;
	private int errorCode;
	private List<GetConfirmResponseParent> responseList;
	private GetConfirmResponseParent response;
}
