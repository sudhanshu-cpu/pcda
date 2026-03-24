package com.pcda.mb.reports.airrequestreport.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAirRqIdParentModel {

	

	private String trRuleNo;
	private String personalNo;
	private String requestId ;
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
	private Integer count;
	private String TravellerName;
	private Integer travelTypeDomInt;
	
	
	
	private List<AirReqIPassengerModel> passengerDetailList; 
	
	private List<AirReqIdQuestionModel> requestQuestionList; 
}
