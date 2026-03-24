package com.pcda.co.requestdashboard.claimreqapproval.model;

import com.pcda.util.ClaimType;

import lombok.Data;

@Data
public class ClaimApprovalReqModel {

	private String tadaClaimId;
	private String personalNo;
	private String payAccntNo;
	private String travelTypeName;
	private String creationTime;
	private String coApprovalDate;

	private Integer totalSpentAmount;
	private Integer totalAdvanceAmount;
	private Integer totalRefundAmount;
	private Integer totalClaimedAmount;

	private Integer passedAmount;
	private Integer totalApprovdRfnd;
	private Integer totalApprovdAdvnc;

	private int claimPriority;
	private ClaimType claimType;

}

