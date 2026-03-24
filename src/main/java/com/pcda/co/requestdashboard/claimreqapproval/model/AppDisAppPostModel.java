package com.pcda.co.requestdashboard.claimreqapproval.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class AppDisAppPostModel {

	private BigInteger loginUserId;
	
	private String claimId;
	
	private String disaproveReason;
	
	private String approveDisApproveAction;
}

