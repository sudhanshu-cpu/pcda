package com.pcda.co.approveuser.approvechangepersonalno.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostApproveModel {

	
	private BigInteger  loginUserId;
	private String userId;	
	private Integer sequenceNo;
	private String remark;
	
}
