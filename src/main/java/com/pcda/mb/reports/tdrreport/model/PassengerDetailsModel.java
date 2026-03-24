package com.pcda.mb.reports.tdrreport.model;

import lombok.Data;

@Data
public class PassengerDetailsModel {
      private String requestId;
      private String dateOfBooking;
      private String boardingDate;
      private String totalFare;
      private String bookingStatus;
      private String currentStatus;

      private String pnrNo;
      private String ticketNoTicketNo;
      private String isApproved;
      private String irctcTktType;
      private String bookingId;
      private String travelTypeName;

      private String  isMaterPassenger;
      private String isOfficial;
      private String isOnGovtInt;
      private String childSno;
      private String childName;
      private String childAge;
      private String childGender;

	private Integer passangerNo;
	private String passangerName;
	private Integer passangerAge;
	private String gender;
      private String paxType;
	private String currentCancelStatus;
      private String paxTdrApprovalState;
	private String trnBerth;
	private String trnCoach;
	private String trnSeat;
	private Double tktBaseFare;
	private Double reservCharge;
	private Double superFastChrg;
	private Double otherChrge;
	private Double concesionAmt;
	private Integer totalPassenger;
      
      
      


}
