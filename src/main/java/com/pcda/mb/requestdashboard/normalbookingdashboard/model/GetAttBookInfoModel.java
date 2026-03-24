package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GetAttBookInfoModel {
	private String bookingId;
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
	
	private List<GetAttFlightInfoModel> flightInfo=new ArrayList<>();
	private List<GetAttPassengerInfoModel> passengerInfo=new ArrayList<>();
	private GetAttInvoiceInfoModel invoiceInfoModel; 
	private List<GetAttPaxInvoiceInfoModel> paxInvoiceInfo=new ArrayList<>();
	private List<GetAttPaxCanInvoiceInfoModel> paxCanInvoiceInfo=new ArrayList<>();
	
}
