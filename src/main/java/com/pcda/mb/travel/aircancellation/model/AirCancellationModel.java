package com.pcda.mb.travel.aircancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class AirCancellationModel {
	
	private String journeyType;
	private String onwardCancel;
	
	private String returnCancel;
	private String operatorTxnId;
	
	private String bookingId;
	private String requestId;
	
	private String origin;
	private String destination;
	
	private String originName;
	private String destinationName;
	
	private String journeyDate;
	private String bookingDate;
	
	private String currentBookingStatus;
	private Integer currentBookingStatusInt;
	private String totalInvoice;
	
	private String totalBaseFare;
	private String cabinClass ;
	
	private String leadMobileNumber;
	private String ledEmailId;
	
	private String canApproved;
	private Integer canApprovedInt;
	private String cancellationReason;
	
	private String ticketType;
	private String roundTrip;
	
	private Integer totalPaxCount;
	private List<FlightDetail> flightDetail;
	
	private List<PassangerDetail> passangerDetail;
	
}

