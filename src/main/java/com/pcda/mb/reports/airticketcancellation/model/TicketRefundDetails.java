package com.pcda.mb.reports.airticketcancellation.model;

import lombok.Data;

@Data
public class TicketRefundDetails implements Comparable<TicketRefundDetails>{
	
	private int passengerNO;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String cancelaltiondate;
	
	private String paxID;
	private String journeySegemnt;
	private String canTaxId;
	private String refundStatus;
	private String refundAmt;
	private String canInvoiceId;
	private String refundDate;

	
	@Override
	public int compareTo(TicketRefundDetails o) {
	
		return   passengerNO-o.getPassengerNO();
	}

	                     

}
