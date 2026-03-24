package com.pcda.mb.requestdashboard.aircancellationdashboard.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostAirDashAbortCanModel {
 
	private String bookingIdAbort;
	private String bookingId;
	private String abortReason;
	private BigInteger loginUserId;
}
