package com.pcda.mb.finalclaim.supplementaryclaim.model;

import lombok.Data;

@Data
public class SupplementaryClaimViewResponse {

	private SupplementaryClaimViewResponseBean response;
	private String errorMessage;
	private Integer errorCode;

}
