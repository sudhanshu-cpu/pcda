package com.pcda.mb.requestdashboard.changeboardingstationdashboard.model;

import java.util.Map;

import lombok.Data;

@Data
public class BoardingStationDetailsModel {
	
	private String pnrNumber;
	private String bookingId;
	private String traninNumber;
	private String bookingStatus;
	private String fromStation;
	private String toStation;
	private String journeyDate;
	private String journeyDateStr;
	private String boardingDate;
	private String ticketNumber;
	private String boardingPoint;
	private String newBoardingPoint;
	private Map<String,String> boardingStationMap;
	
	

}
