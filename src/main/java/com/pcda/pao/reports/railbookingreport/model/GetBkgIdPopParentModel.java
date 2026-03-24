package com.pcda.pao.reports.railbookingreport.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBkgIdPopParentModel {

	
	
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
	
	private List<GetBkgIdPopChildModel> ticketPassangerDetails ;	
}
