package com.pcda.mb.requestdashboard.aircancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAirDashBkgIdParentModel {
	private String bookingId;
	private String requestId;
	private String origin;
	private String destination;
	private String originName;
	private String destinationName;
	private String journeyDate;
	private String bookingDate;
	private String ticketType;
	private Double totalInvoice;
	private Double totalBaseFare;
	private String cabinClass;
	private String operatorTxnId;
	private String leadMobileNo;
	private String leadEmailId;
	private String currentBookingStatus;
	private int currentBookingStatusInt;
	private String canApproved;
	private int canApprovedInt;
	private String cancellationReason;
	private String roundTrip;
	private int totalPaxCount;
	private String serviceProvider;
	
	
	List<GetAirDashFlightDetail> flightDetail;
	List<GetAirDashPassangerDetail> passangerDetail;

}
