package com.pcda.mb.requestdashboard.viarequestlegrebooking.model;

import java.util.Date;

import lombok.Data;

@Data
public class ViaRequestLegReBookingModel {

	private String requestId;
	private String bookingStatus;	
	private String fromStn;	
	private String toStn;	
	private String fromStationName;
	private String toStationName;
	private Date journeyDate;
	private String approvalState;
	
	private String jurneyDateFormat="";
	private String rebooking; 
	private String journeyType;	
	private String isTatkalApproved;
	private int sequenceNo;
	private int clusteredFlag;
	private int tktSequenceNo;
	private String encryptedReqId;
	


}

