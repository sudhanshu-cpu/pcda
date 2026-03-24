package com.pcda.mb.reports.exceptionalbookingreport.model;

import lombok.Data;

@Data
public class GetExceptionalReportData {
	private String bookingId;
	private String pnrNumber;
	
	private String accountOffice;
	private String personalNo;
	
	private String ticketType;
	private String tatkalApprovalStatus;
	
	private String tatkalBooking;
	private String bookingDate;
	private String unitName	;
	private Double totalFare;
	private String canView;
	
	private Integer drtoPAO;
	private Integer crtoPAO;

	private String journeyDate;
	private String travelTypeName	;
}
