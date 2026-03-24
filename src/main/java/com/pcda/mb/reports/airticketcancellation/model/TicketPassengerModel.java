package com.pcda.mb.reports.airticketcancellation.model;

import lombok.Data;

@Data
public class TicketPassengerModel implements Comparable<TicketPassengerModel>{
	
	private Integer passSeqNo;
	private Integer passengerNo;
	private String invoiceNo;
	private String pnrNo;
	private String ticketNo;
	private String onwardPaxID;
	private Integer onwardCancelInt;
	private String onwardCancelStr;
	private String returnPaxID;
	private Integer returnCancelInt;
	private String returnCancelStr;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String relation;
	private Integer age;
	private String dob;
	private String mobileNo;
	private String emailID;
	private Integer passProfileSeq;
	private String gender;
	private String leadPassenger;
	private String ticketBookingStatus;
	@Override
	public int compareTo(TicketPassengerModel o) {
		
		return   passengerNo-o.getPassengerNo();
	}
	

	
	
}
