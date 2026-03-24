package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class AirRequestData {

	private String trRuleNo;
	private String requestId;
	private String personalNo;
	private String travelType;
	private String codeHead;
	private String accountOffice;
	private String origin;
	private String destination;
	private String requestDate;
	private String journeyDate;
	private String cabinClass;
	private String approvalStatus;
	private String bookingType;
	private String status;
	private String relation;
	private int count;
	private String travellerName;
	private List<AirRequestPassData> passData;
	private List<AirRequestQuestionData> questionData;
	private String message="";

	
}
