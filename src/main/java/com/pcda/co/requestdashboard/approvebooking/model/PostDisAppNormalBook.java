package com.pcda.co.requestdashboard.approvebooking.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostDisAppNormalBook {

	private String reasonForDisapprove;
	private String  requestId;
	private String  travelMode;
	private BigInteger loginUserId;
	private String personalNo;
}
