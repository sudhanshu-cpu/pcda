package com.pcda.mb.travel.exceptionalcancellation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetSearchModel {

	
	private String requestId;
	private String bookingDate;
	private String trainNo;
	private String journeyDate;
	private Double totalAmount;
	private String bookingStatus;
	private String pnrNo;
	private String ticketNo;
	private String isApproved;
	private Integer irctcTktType;
	private String travelTypeName;
	private String bookingId;
	private String travelType;

	
}
