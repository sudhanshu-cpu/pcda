package com.pcda.mb.reports.airticketcancellation.model;

import lombok.Data;

@Data
public class AirTicketCancellationModel {
	private String operatorTxnId;
	private String bookingId;
	private String requestId;
	private String origin;
	private String destination;
	private String journeyDate;
	private String journeyDateStr;
	
	
}
