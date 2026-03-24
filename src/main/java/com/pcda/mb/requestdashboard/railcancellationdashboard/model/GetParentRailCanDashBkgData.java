package com.pcda.mb.requestdashboard.railcancellationdashboard.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.pcda.mb.travel.exceptionalcancellation.model.BookingStatus;
import com.pcda.mb.travel.exceptionalcancellation.model.RailTicketType;

import lombok.Data;

@Data
public class GetParentRailCanDashBkgData {


	private String requestId;
	private Date dateOfBooking;
	private Date boardingDate;
	private Double totalFare;
    private BookingStatus currbookingStatus;
	private String pnrNo;
	private String ticketNoTicketNo;
	private String isCanApproved;
    private RailTicketType irctcTktType;
	private String bookingId;
	private String travelTypeName;
	private String travelType;
	private String trainNo;
	private Date journeyDate;  
	private String journeyDateStr;
	private String ticketNo;
	private BigInteger travelerUserId;
	private String personalNo;
	private String journeyClass;
	private Date bookingDate;
	private String bookingDateStr;
	private Double totalAmount;
	private Double totalServiceTax;
	private String isPartyBooking;
	private String trainName;
	private Double irlFare;
	private String journeyQuota;
	private Double irctcSerCharge;
	private String frmStn;
	private String toStn;
	private String canceltnReason;
	private Integer noOfAdults;        
	private String operatorTxnId;
    private int totalPax;
   
	private List<GetChildRailCanDashbkgData> passengerList ;
}
