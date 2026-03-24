package com.pcda.mb.requestdashboard.railcancellationdashboard.model;

import lombok.Data;

@Data
public class GetRailCanDashDataModel {

	
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
	private String operatorTxnId;
    private int totalNoPax;
}
