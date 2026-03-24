package com.pcda.mb.finalclaim.settledclaims.model;

import lombok.Data;

@Data
public class SettledClaimModel {
	
	private String tadaClaimId;
	private String personalNo;
	
	private String travelTypeName;
	private String creationTime;
	
	private String coApprovalDate;
	private int totalAmntSpent;
	
	private int totalAdvnc;
	private int totalRefund;
	
	private int claimedAmnt;
	private int totalApprvdAdvance;
	
	private String claimType;
	private String travelID;
	
	private String claimPriority;
	private int totalApprovedRefund;
	
	private int passedAmnt;

}

