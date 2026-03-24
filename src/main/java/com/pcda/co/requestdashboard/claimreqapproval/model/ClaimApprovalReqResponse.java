package com.pcda.co.requestdashboard.claimreqapproval.model;

import java.util.List;

import lombok.Data;

@Data
public class ClaimApprovalReqResponse {

	private List<ClaimApprovalReqModel> responseList;
	private int errorCode;
	private String errorMessage;

}
