package com.pcda.mb.reports.railtravelreports.model;

import lombok.Data;

@Data
public class GetRailTravelReportModel {
	
	private String bookingId;
	private String pnrNumber;
	
	private String accountOffice;
	private String personalNo;
	
	private String ticketType;
	private String tatkalApprovalStatus;
	
	private String tatkalBooking;
	private String bookingDate;
	
	private Double totalFare;
	private String canView;
	
	private Integer drtoPAO;
	private Integer crtoPAO;

	private String journeyDate;
	private String travelTypeName;
	
	private String name;
	private String fromStation;
	private String toStation;
	private Integer noOfPassenger;
	private String trRule;
	private String transactionId;
	private String trainNo;
	private String cabinClass;
	
	
	

	
	
}
