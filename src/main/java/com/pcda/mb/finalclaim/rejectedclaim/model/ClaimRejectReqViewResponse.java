package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class ClaimRejectReqViewResponse {

	private List<ClaimRejectReqView> responseList;
	private Integer errorCode;
	private String errorMessage;

}
