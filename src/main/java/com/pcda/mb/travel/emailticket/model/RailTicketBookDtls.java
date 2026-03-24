package com.pcda.mb.travel.emailticket.model;

import com.pcda.util.Gender;

import lombok.Data;

@Data
public class RailTicketBookDtls implements Comparable<RailTicketBookDtls> {

	private Integer seqNo;
	private Integer passengerType;
	private String name;
	private Integer age;
	private Gender gender;
	private Integer isConcession;
	private String tktCurrentStatus;
	private String tktBookingStatus;

	private String berth;
	private String coach;
	private String seat;
	private String idCardType;
	private String idCardNo;

	private Double tktBaseFare;
	private Double diffAmt;
	private Double concesionAmt;
	private Double reservCharge;
	private Double superFastChrg;
	private Double safetyChrg;
	private Double otherChrge;
	private Double serviceTax;
	private Double atrTatkalFare;

	private String canceltnDate;

	@Override
	public int compareTo(RailTicketBookDtls o) {
		
		return this.getSeqNo() - o.getSeqNo();
	}
}
