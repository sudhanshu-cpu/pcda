package com.pcda.mb.travel.railcancellation.model;

import com.pcda.util.Gender;

import lombok.Data;

@Data
public class GetCancellationPostData {

	private String  cancellationString;
	private String bookingId;
	private String ticketNo;	
	private String check;	
	private String  groupId;	
	private String  cancelReason;
	private String  frmReq;
	private String dodBookingRefNo;
	private Integer  passangerNo;	
	private String  passangerName;
	private Integer  passangerAge;
	private Gender  passangerGender;
	private String  trnCoach;	
	private String  trnSeat;	
	private String  trnBerth;	
	private String  bookingStatus;	
	private String  currentStatus;	
	private String  cancelStatus;
	int totalNoPax =0;	
}
