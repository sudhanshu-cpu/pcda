package com.pcda.co.requestdashboard.claimreqapproval.model;

import java.util.List;

import lombok.Data;

@Data
public class PostClaimAppDisModelResponse {

	
	private String errorMessage;

	private Integer errorCode;

	private AppDisAppPostModel response;

	private List<AppDisAppPostModel> responseList;
}
