package com.pcda.co.approveuser.transferinapproval.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PosttransferInApprovalModel {
	
	private BigInteger userId;
	private Integer sequence;
	private String personalNo;
	private String transferTo;
	private String unitServiceId;
	private String groupId;
	private BigInteger loginUserId;
	private String loginVisitorUnitPaoId;
	private String loginVisitorUnitAirPaoId;
	private String remark;
	private String reason;
	private String event;
	
}






