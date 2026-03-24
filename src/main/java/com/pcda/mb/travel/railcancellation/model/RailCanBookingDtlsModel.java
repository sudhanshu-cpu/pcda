package com.pcda.mb.travel.railcancellation.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.pcda.util.BookingStatus;
import com.pcda.util.RailTicketType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RailCanBookingDtlsModel {

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
       private String ticketNo;
       private BigInteger travelerUserId;
       private String personalNo;
       private String journeyClass;
       private Date bookingDate;
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
       private List<PassengerRailCanDetailsBean> passengerList;
	
       private int bookAdultCount;
	
       private String bookingDateStr;
   	private String journeyDateStr;
	
	           
	
	
}
