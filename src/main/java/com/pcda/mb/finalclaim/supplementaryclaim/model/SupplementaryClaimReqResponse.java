package com.pcda.mb.finalclaim.supplementaryclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class SupplementaryClaimReqResponse {

	private String errorMessage;
	private Integer errorCode;
	private List<ClaimSuppResBean> responseList;

}
