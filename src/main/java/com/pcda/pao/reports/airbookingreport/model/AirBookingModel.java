package com.pcda.pao.reports.airbookingreport.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class AirBookingModel {

	private String bookingId;
	private String bookingType;
	private String accountOfficeName;
	private String groupName;
	private String userAlias;
	private String travelType;
	private String cabinClass;
	private String bookingDate;
	private String journeyDate;
	private BigInteger totalInvoice;
	private String currBookingStatus;
	private String serviceProvider;
	private String onwardIsOnGovt;
	private Integer canTotalRefund;
	
}
