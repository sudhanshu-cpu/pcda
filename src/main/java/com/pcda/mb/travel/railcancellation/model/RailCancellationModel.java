package com.pcda.mb.travel.railcancellation.model;





import com.pcda.util.RailTicketType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RailCancellationModel {

	private String requestId;
	private String bookingDate;
	private String trainNo;
	private String journeyDate;
	
	private Double totalAmount;
	private String bookingStatus;
	private String pnrNo;
	private String ticketNo;
	private String isApproved;
	private RailTicketType irctcTktType;
	private String travelTypeName;
	private String bookingId;
	private String travelType;
		
	
	
	
	
}
