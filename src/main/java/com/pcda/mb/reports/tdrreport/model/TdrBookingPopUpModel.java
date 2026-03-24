package com.pcda.mb.reports.tdrreport.model;

import java.util.List;

import lombok.Data;

@Data
public class TdrBookingPopUpModel {

 private String groupId;
 private String requestId;
 private String dateOfBooking;
 private String boardingDate;
 private String totalFare;
 private String currbookingStatus;
 private String pnrNo;
 private String ticketNoTicketNo;
 private String isCanApproved;
 private String irctcTktType;
 private String bookingId;
 private String travelTypeName;
 private String travelType;
 private String trainNo;
 private String journeyDate;
 private String ticketNo;
 private String travelerUserId;
 private String personalNo;
 private String journeyClass;
 private String bookingDate;
 private Double totalAmount;
 private String totalServiceTax;
 private String isPartyBooking;
 private String trainName;
 private String irlFare;
 private String  journeyQuota;
 private Double irctcSerCharge;
 private String frmStn;
 private String toStn;
 private String canceltnReason;
 private String noOfAdults;
 private String accountOffice;
 private Integer distance;
 private String scheduleDepartre;
 private String tdrApprovalState;
 private String tdrMBGround;
 private String tdrCOGround;
 private String tdrReason;
 private String tdrDate;
 
 /* Below Fields are used on the TDR-POPUP Page To Show Formatted Dates */
 private String tdrFormattedDate;
 private String journeyFormattedDate;
 private String bookingFormattedDate;
 private String boardingFormattedDate;
 

 private List<PassengerDetailsModel> tdrRailPaxDetailsBean;

}
