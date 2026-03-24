package com.pcda.mb.travel.bulkBkg.model;

import java.util.List;

import lombok.Data;

@Data
public class TravelRequestBulkBkgData {
	
	private Integer journeyType;
	private boolean reqStatus;
	private String errorMessage;
	private int travelMode;
	private String ticketType;
	private String name;
	private String officeId;
	private String requestId;
	private String personalNumber;
	private String refRequestId;
	private String bookingType;
	private String travelRule;
	private String travelType;
	private String bulkBkgId;
	private AirRequestBulkBkgRoute airRequestRoute;	
	private List<RequestBulkBkgPassengers> requestPassengers;
	private List<CombineRequestBulkBkgRoute> combinedBean;


}
