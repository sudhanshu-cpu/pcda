package com.pcda.mb.finalclaim.draftclaim.model;

import lombok.Data;

@Data
public class DraftClaimRequestResponse {

	private Integer errorCode;
	private String errorMessage;
	private DraftClaimRequestBean response;

}
