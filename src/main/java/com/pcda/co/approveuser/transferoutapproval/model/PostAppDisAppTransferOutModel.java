package com.pcda.co.approveuser.transferoutapproval.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostAppDisAppTransferOutModel {
	
	private BigInteger userId;
	private Integer sequence;
	private String personalNo;
	private String oldUnit;
	private BigInteger loginUserId;
	private String currentUnit;
	private String reason;
	private String event;
	
}


