package com.pcda.pao.reports.railbookingreport.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRailCancelParentModel {
	private String  requestId ; 
	private String  bookingId ; 	
	private String  travelType ; 
	private String  journeyClass ; 
	private String  pnrNumber ; 	
	private String  ticketNumber ; 
	private String  trainNumber ; 
	private String  trainName ; 	
	private String  bookingDate ; 
	private String  journeyDate ; 
	private double  totalFare ; 		
	private String  tatkalBookingState;
	private String  tatkalApprovalState;
	private double  irctcServiceCharge;
	private String  fromStnCode;
	private String  toStnCode;
	private String  fromStnName;
	private String  toStnName;
	
	
	   
	private List<GetRailCancelChildModel> cancellationView;
}
