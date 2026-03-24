package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class RailRequestData {

	private String createdBy;
	private String approvedBy;
	private String bookingType;
	private String travelType;
	private String travelRule;
	private String requestID;
	private String bookingStatus;
	private String name;
	private String pesrNo;
	private String originStation;
	private String destStation;
	private List<RailReqDetails> requestedDetails;
	private List<RailPassangerDetails> passDetails;
	private String message="";
	
	
}
