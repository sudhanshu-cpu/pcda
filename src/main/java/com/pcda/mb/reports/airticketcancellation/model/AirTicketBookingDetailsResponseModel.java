package com.pcda.mb.reports.airticketcancellation.model;

import java.util.List;

import lombok.Data;

@Data

public class AirTicketBookingDetailsResponseModel {

   private String oid;
   private String bookingID;
   private String requestId;
   private String originCode;
   private String destinationCode;
   private String originName;
   private String destinationName;
   private String journeyDate;
   private String bookingDate;
   private String bookingStatus;
   private String ticketType;
   private String isCanApproved;
   private Integer totalInvoice;
   private Integer totalBaseFare;
   private String cabinClass;
   private String operatorTxnId;
   private String leadMobileNumber;
   private String leadEmailId;
   private String cancellationReason;
   private String cfAvailFlag;
   private String cfRemarks;
   
   private List<FlightDetailsModel> flightDetails;
   private List<TicketPassengerModel> ticketPassangerDetails;
   private List<TicketRefundDetails> ticketRefundDetails; 	
}
