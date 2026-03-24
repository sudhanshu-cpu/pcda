package com.pcda.co.requestdashboard.approveaircancellation.model;

import java.util.Map;

import lombok.Data;
@Data
public class PostAirCancellationApprovalModel {
	

	
	private String isRoundTrip;
	
	private String operationMode; 
	
	private String reasonForDisapprove;
	
	private Integer totalPassanger;
	
	private String bookingId;
	
	private Map<String, Integer>onwardCheck;
	
	private Map<String, Integer>returnCheck;
	
	

	

}
