package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import lombok.Data;

@Data
public class GetATTBulkBkgFlightInfoModel implements Comparable<GetATTBulkBkgFlightInfoModel> {
	
	private int stop;
	private int seq;
	private String operatingAirline;
	private String airline;
	private String scheduledArrival;
	private String scheduledArrivalDate;
	private String scheduledDeparture;
	private String scheduledDepartureDate;
	private String source;
	private String destination;
	private String sourceCode;
	private String destinationCode;
	private String departTerminal;
	private String arrivTerminal;
	private String airlinePnr;
	private String gdsPnr;
	private String segmentOriginAirport;
	private String segmentDestinationAirport;
	private String flightNo;
	private String cabinClass;
	private String bookingClass;
	private String refundable;
	private String sectorDuration;
	private String baggageAllowance;
	@Override
	public int compareTo(GetATTBulkBkgFlightInfoModel o) {
		
		return this.getSeq()-o.getSeq();
	}

}
