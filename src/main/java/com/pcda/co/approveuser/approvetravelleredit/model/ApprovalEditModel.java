package com.pcda.co.approveuser.approvetravelleredit.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class ApprovalEditModel {

	private int approvalType;
	private BigInteger loginUserId;
	private BigInteger userId;
	private int seqNo;
	private String disaproveReason;
	private String userAlias;

}
