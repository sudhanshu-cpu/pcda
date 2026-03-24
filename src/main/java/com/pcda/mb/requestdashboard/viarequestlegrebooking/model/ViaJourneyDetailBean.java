package com.pcda.mb.requestdashboard.viarequestlegrebooking.model;

import java.util.Date;

import lombok.Data;

@Data
public class ViaJourneyDetailBean {

	 private String requestId="";
	 private Date journeyDate;
	 private String travelerName="";
	 private String personalNo="";
	private Integer serialNo;
	private String userId="";
	private String travelTypeId="";
	private String travelType;
	private String encryptedId;
	private boolean rebooking;
	private String requestStatus="";
	private String empCode;
	private int sequenceNo;
	private int tktSequenceNo;
	private String approvalState;
	private String fromStation;
	private String toStation;
	private String fromStationName;
	private String toStationName;	
	private String fullHalfTkt;
	private Date creationTime;
	private String bookingType;
	private String isTatkalApproved;
	private String adultChild;	
	private int clusterFlag;

}
