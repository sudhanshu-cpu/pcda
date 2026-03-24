package com.pcda.mb.finalclaim.rejectedclaim.model;

import lombok.Data;

@Data
public class RejectedClaimRequestResponse {

	private RejectedClaimRequestBean response;
	private String errorMessage;
	private Integer errorCode;

}
