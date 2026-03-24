package com.pcda.mb.reports.railtravelreports.model;

import lombok.Data;

@Data
public class RailTravelInputtModel {
	

	private String bookingId="";
	private String requestId="";
	
	private String pnrNo="";
	private String ticketNo="";
	
	private String fromDate="";
	private String toDate="";
	
	private String fromJourneyDate="";
	private String toJourneyDate="";
	
	private String traveType="";
	private String codeHead="";
	
	private Integer bookingStatus;
	private String personalNo="";
	
	private Integer tatkalFlag;
	private String accountOffice="";
	
	private Integer ticketType;
	private String unitOffice="";
	
	
}
