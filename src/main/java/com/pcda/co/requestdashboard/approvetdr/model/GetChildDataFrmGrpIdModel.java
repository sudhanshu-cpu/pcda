package com.pcda.co.requestdashboard.approvetdr.model;

import com.pcda.util.BerthPref;
import com.pcda.util.BookingStatus;
import com.pcda.util.FoodPref;
import com.pcda.util.PassengerType;
import com.pcda.util.TdrStatus;
import com.pcda.util.YesOrNo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetChildDataFrmGrpIdModel {

	private String requestId;
	private String dateOfBooking;
	private String boardingDate;
	private String totalFare;
    private String bookingStatus;
    private String currentStatus;
    private BookingStatus  currentCancelStatus;
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
	private Integer  childSno;
	private String  childName;
	private Integer childAge;
	private Integer childGender;
	private BerthPref trnBerthPref;
	private YesOrNo isConcession;
	private FoodPref foodPrefernce;
	private String seat;
	private String tdrReason;
	private TdrStatus tdrAprrovalState;
	private YesOrNo isTdrFile;
	private Integer totalPassenger;
	
	private Double concesionAmt;
	private PassengerType paxType;
	private Double otherChrge;
	private Double reservCharge;
	private Double superFastChrg;
	private Double tktBaseFare;
	
	private String passSex;
	
}
