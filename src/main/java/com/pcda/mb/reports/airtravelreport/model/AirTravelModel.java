package com.pcda.mb.reports.airtravelreport.model;

import java.math.BigInteger;

import lombok.Data;
@Data
public class AirTravelModel {

	
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
	private String name;
	private String fromStation;
	private String toStation;
	private Integer noOfPassenger;
	private String trRule;
	private String transactionId;
	private String flightNo;
}
