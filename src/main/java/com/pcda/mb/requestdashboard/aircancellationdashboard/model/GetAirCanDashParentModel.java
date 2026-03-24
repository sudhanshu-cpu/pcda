package com.pcda.mb.requestdashboard.aircancellationdashboard.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class GetAirCanDashParentModel {

	private String bookingId;
	private String requestId;
	private String origin;
	private String destination;
	private String originName;
	private String destinationName;
	private String journeyDate;
	private String bookingDate;
	private String currentBookingStatus;
	private BigInteger totalInvoice;
	private String operatorTxnId;
	private String canApproved;
	private BigInteger canApprovedInt;
	private String cancellationReason;
    private String	serviceProvider;
}
