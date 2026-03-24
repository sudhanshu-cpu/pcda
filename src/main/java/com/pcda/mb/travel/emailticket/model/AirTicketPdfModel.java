package com.pcda.mb.travel.emailticket.model;



import java.util.List;

import lombok.Data;

@Data
public class AirTicketPdfModel {

	private String  bookingId;
	private String 	requestId;
	
	private String  bookingDate;
	private String 	bookingStatus;
	
	private String  groupId;
	private String 	operatorTxnId;
	
	private String  journyType;
	private int bookedSegment;
	
	private String  sourceSectorCode;
	private String 	destinationSectorCode;
	
	private String  sourceSectorAirport;
	private String 	destinationSectorAirport;
	
	private String  showLTCLabel;
	private String sourceSectorCity;
	
	private String  destinationSectorCity;
	
	private String airIATANo;
	private String airGSTNo;
	
	private String journeyDate;
	
	private int serviceProvider;

	private String trRule;
	
	private List<FlightInfo> flightInfo;
	
	private List<PassengerInfo> passengerInfo;
	
	private InvoiceInfoModel invoiceInfoModel;
	
	private List<PaxInvoiceInfo> paxInvoiceInfo;
	
	private List<PaxCanInvoiceInfo> paxCanInvoiceInfo;
	
	
}
