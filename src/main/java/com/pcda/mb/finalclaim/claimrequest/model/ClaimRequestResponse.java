package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class ClaimRequestResponse {

	private String response;
	private Integer errorCode;
	private String errorMessage;

}
