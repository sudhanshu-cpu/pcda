package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.Date;

import lombok.Data;

@Data
public class ClaimRejectReqView {

	private String tadaClaimId;
	private String personalNo;
	private String cdacAccNo;
	private String travelType;
	private Date creationTime;
	private int status;
	private Date claimReceivedOn;
	private Double totalAmountSpent;
	private Double claimedAmount;
	private Double totalAdvance;
	private Double totalRefund;
	private String priority;

}
