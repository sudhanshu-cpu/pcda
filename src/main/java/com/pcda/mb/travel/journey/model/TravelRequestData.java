package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class TravelRequestData {

	private Integer journeyType;
	private boolean reqStatus;
	private String errorMessage;
	private int travelMode;
	private String ticketType;
	private String isTatkal;
	private String name;
	private String officeId;
	private String requestId;
	private String personalNumber;
	private String refRequestId;
	private String bookingType;
	private String warrantIssueDate;
	private String warrantReason;
	private String travelRule;
	private String travelType;
	private String daAdvanceAvailed;

	private AirRequestRoute airRequestRoute;
	private List<RailRequestRoute> railRequestRoute;
	private DaAdvanceInfo daAdvance;
	private CTGDetails ctgDetails;
	private List<RequestPassengers> requestPassengers;
	private List<CombineRequestRoute> combinedBean;

}
