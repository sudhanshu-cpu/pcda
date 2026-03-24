package com.pcda.co.approveuser.transferinandreemloyeemet.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostTranInReempApprovalModel {

	
	private BigInteger userId;
	private Integer sequence;
	
	private BigInteger loginUserId;
	private String profileChangeAtrribute;
	
	private String personalNo;
	private String currentUnit;
	
	private String transferTo;
	private String unitServiceId;
	
	private String oldUnit;
	private String loginVisitorUnitPaoId;
	
	private String reason;
	private String event;
	
	
}



