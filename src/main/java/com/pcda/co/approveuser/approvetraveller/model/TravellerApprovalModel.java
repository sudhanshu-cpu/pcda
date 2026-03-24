package com.pcda.co.approveuser.approvetraveller.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class TravellerApprovalModel {

	private int approvalType;

	private String userAlias;

	private String remarks;

	private BigInteger userId;

	private BigInteger approvedBy;

	private String actionType;

}
