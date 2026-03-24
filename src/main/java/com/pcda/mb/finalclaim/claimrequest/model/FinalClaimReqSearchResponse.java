package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.List;

import lombok.Data;

@Data
public class FinalClaimReqSearchResponse {

	private List<FinalClaimReqSearchModel> responseList;

	private int errorCode;

	private String errorMessage;

}
