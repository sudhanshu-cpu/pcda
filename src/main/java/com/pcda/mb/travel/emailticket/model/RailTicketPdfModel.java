package com.pcda.mb.travel.emailticket.model;

import java.util.List;

import lombok.Data;

@Data
public class RailTicketPdfModel {

	private List<RailTicketBookDtls> ticketBookDtls;

	private Double atrTatkalFare;
	private String ticketUserAltId;

	private String requestId;
	private String quota;

	private String boardingDate;
	private Integer isTatKal;

	private String pnrNo;
	private String trainNo;

	private String trainName;
	private String ticketNo;

	private String bookingDate;
	private String jrnyClass;

	private String fromStation;
	private String toStn;

	private String boardingPt;
	private String jrnyDate;

	private String scheduleDepartre;
	private String reservUpto;

	private Integer distance;
	private String arrivalTime;

	private String mobNo;
	private Double serviceTax;

	private Double baseFare;
	private Double irctcServCharg;

	private Double totalFare;
	
	private int irctcTktType;
	
	private int requestSeqNo;

	private String trRule;

}
