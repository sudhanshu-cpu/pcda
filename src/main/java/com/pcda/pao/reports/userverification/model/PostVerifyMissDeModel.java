package com.pcda.pao.reports.userverification.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostVerifyMissDeModel {

	private BigInteger userId;
	private int oldPaoVerificationStatus;
	private BigInteger loginUser;
	private String comment;
	private String userAction;
	
}



