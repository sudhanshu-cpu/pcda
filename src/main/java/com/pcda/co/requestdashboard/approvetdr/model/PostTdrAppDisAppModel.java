package com.pcda.co.requestdashboard.approvetdr.model;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostTdrAppDisAppModel {
     private String event;
	private String bookingId;
	private BigInteger loginUserId;
	private int isTdrGovtInt;
	private String disaproveReason;
}
