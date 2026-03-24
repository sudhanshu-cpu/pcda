package com.pcda.co.requestdashboard.approveaircancellation.model;

import java.math.BigInteger;
import java.util.List;



import lombok.Data;

@Data
public class GetApproveAirCancellationModel {

	private String bookingId;
	private String requestId;
	private String origin;
	private String destination;
	private String originName;
	private String destinationName;

	private String journeyDate;
	private String bookingDate;

	private String currentBookingStatus;

	private BigInteger totalInvoice;

	private String operatorTxnId;
	private String canApproved;
	private BigInteger canApprovedInt;

	private String cancellationReason;

	//
	private String journeyType;
	private String onwardCancel;
	
	private String returnCancel;
	
	
	
	
	
	private Integer currentBookingStatusInt;
	
	private String totalBaseFare;
	private String cabinClass ;
	
	private String leadMobileNumber;
	private String ledEmailId;
	
	
	private String ticketType;
	private String roundTrip;
	
	private Integer totalPaxCount;
	private List<FlightDetail> flightDetail;
	
	private List<PassangerDetail> passangerDetail;
	
}
