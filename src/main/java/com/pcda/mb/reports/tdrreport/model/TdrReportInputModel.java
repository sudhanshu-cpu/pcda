package com.pcda.mb.reports.tdrreport.model;

import lombok.Data;

@Data
public class TdrReportInputModel {
	
	private String bookingId ="";        
	private String requestId = "";
	private String pnrNo = "";
	private String tdrStatus = "";
	private String personalNo= "";
	private String accountOfice= "" ;
	private String travelType = "";
	private String fromDate = "";
	private String toDate = "";
}
