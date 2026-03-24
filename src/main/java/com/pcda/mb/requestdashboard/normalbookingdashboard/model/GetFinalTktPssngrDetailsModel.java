package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetFinalTktPssngrDetailsModel {
	private String passengerSerialNumber;
	private String passengerName;
	private String passengerAge;
	private String passengerGender;
	private String passengerBedrollChoice;
	private String passengerBerthChoice;
	private String passengerConcession;
	private String passengerIcardFlag;
	private String bookingBerthCode;
	private String bookingBerthNo;
	private String bookingCoachId;
	private String bookingStatus;
	private Integer bookingStatusIndex;
	private String currentBerthCode;
	private String currentBerthNo;
	private String currentCoachId;
	private String currentStatus;
	private Integer currentStatusIndex;
	private String currentBerthChoice;
	private Double passengerNetFare;

}
