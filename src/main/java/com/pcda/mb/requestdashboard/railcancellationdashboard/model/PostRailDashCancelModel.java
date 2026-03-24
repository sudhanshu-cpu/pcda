package com.pcda.mb.requestdashboard.railcancellationdashboard.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostRailDashCancelModel {
	private String transactionId;
	private String cancelString;
	private String dodRefNo;
	private int totalPsnger;
	private String bookingId;
	private String tktCanType;
	private BigInteger loginUserId;
}
