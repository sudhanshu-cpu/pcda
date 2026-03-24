package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GetATTBulkBkgInfoModel {
	
	private String bulkBkgId;
	private String requestId;
	private String bookingDate;
	private String bookingStatus;
	private String groupId;
	private String operatorTxnId;
	private String journyType;
	private Integer bookedSegment;
	private String sourceSectorCode;
	private String destinationSectorCode;
	private String sourceSectorCity;
	private String destinationSectorCity;
	private String sourceSectorAirport;
	private String destinationSectorAirport;
	private String showLTCLabel;
	private String journeyDate;
	private String airGSTNo;
	private String airIATANo;
	private double totalEducess;
	private double totalHigherEducess;
	private int serviceProvider;
	
	private List<GetATTBulkBkgFlightInfoModel> flightInfo=new ArrayList<>();
	private List<GetATTBulkBkgPassengerInfoModel> passengerInfo=new ArrayList<>();

}
