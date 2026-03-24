package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class JourneyDetails {

	private String bookingID;
	private String travelMode;
	private Integer travelmodeInt;
	private String bookingNo;
	private Integer currBookingStatus;
	private String bookingStatus;

	private String journeyYear;
	private String journeyType;
	private Integer journeyTypeInt;
	private String requestId;
	private Double bookingAmount;
	private String claimedAmount;

	private String journeyEndDate;

	private String journeyClass;
	private String fromStation;
	private String toStation;
	private String journeyDate;
	private Double refundAmount;

	private JourneyFamily journeyFamily;

}
