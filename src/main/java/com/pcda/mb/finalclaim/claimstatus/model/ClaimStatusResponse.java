package com.pcda.mb.finalclaim.claimstatus.model;

import java.util.List;

import lombok.Data;

@Data
public class ClaimStatusResponse {

	
	private String errorMessage;

	private Integer errorCode;

	private ClaimStatusModel response;

	private List<ClaimStatusModel> responseList;
}
