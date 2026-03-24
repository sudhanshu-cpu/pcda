package com.pcda.mb.reports.exceptionalbookingreport.model;

import java.util.List;

import lombok.Data;

@Data
public class GetExcepBkgDetailsModel {

	private String requestId;
	private String bookingId;
	
	private String unitOffice;
	private String accountOffice;
	
	private String bookingStatus;
	private String journeyClass;
	
	private String pnrNo;
	private String ticketNo;
	
	private String trainNo;
	private String trainName;
	
	private String from;
	private String to;
	
	private Integer distance;
	private String bookingDate;
	
	private String journeyDate;
	private String scheduleDep;
	
	private Double irctcServiceCharges;
	private Double totalFare;
	
	private List<ExcepTicketsPassangerDetails> ticketPassangerDetails ;
}
