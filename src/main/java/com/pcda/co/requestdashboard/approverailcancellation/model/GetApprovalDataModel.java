package com.pcda.co.requestdashboard.approverailcancellation.model;



import java.util.Date;

import com.pcda.util.BookingStatus;
import com.pcda.util.RailTicketType;

import lombok.Data;

@Data
public class GetApprovalDataModel {

	private String pnrNo;
	private String ticketNo;
	private String trainNo;
	private Date bookingDate;
	private String bookingId;
	private RailTicketType irctcTktType;
	private Date journeyDate;
	private double totalAmount;
	private String requestId;
	
	private String bookingDateStr;
	private String journeyDateStr;
	private BookingStatus currbookingStatus;

}
