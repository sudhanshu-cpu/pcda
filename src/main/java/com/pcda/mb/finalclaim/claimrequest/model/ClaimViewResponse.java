package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class ClaimViewResponse {

	private ClaimViewResponseBean response;
	private String errorMessage;
	private int errorCode;

}
