package com.pcda.pao.reports.airbookingreport.model;

import lombok.Data;

@Data
public class AirBookingReportInputModel {

	private String bookingId;
	private String requestId = "";
	private String fromDate = "";
	
	private String toDate = "";
	private String fromJourneyDate = "";
	private String toJourneyDate = "";
	private String personalNo = "";
	private String traveType = "";
	private Integer bookingStatus ;
	private Integer serviceProvider;
	private String accountOffice = "";
	private String unitOffice = "";
	
	
	

	
}
