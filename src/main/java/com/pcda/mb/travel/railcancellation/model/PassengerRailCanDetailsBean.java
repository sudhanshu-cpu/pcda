package com.pcda.mb.travel.railcancellation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRailCanDetailsBean {
	private String requestId;
	private String dateOfBooking;
	private String boardingDate;
	private String totalFare;
	private String bookingStatus;
	private String currentStatus;
	private String currentCancelStatus;
	private String pnrNo;
	private String ticketNoTicketNo;
	private String isApproved;
	private String irctcTktType;
	private String bookingId;
	private String travelTypeName;
	private String travelType;

	private Integer passangerNo;
	private String passangerName;
	private Integer passangerAge;
	private Integer passangerSex;
	private String trnCoach;
	private String trnSeat;
	private String trnBerth;
	private String isMaterPassenger;
	private String isOfficial;
	private String isOnGovtInt;
	private Integer childSno;
	private String childName;
	private Integer childAge;
	private Integer childGender;

}
