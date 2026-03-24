package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostIRPreConfirmModel {

	
	private String trainNo;
	private String trainName;
	private String sourceStnCode;
	private String sourceStnName;
	private String destStnCode;
	private String destStnName;
	private String depTimeSrc;
	private String arrTimeDest;
	private String jrnyDate;
	private String jrnyClass;
	private String isShowChangeStnVal;
	private String trainCategory;
	private String trainTypeVal;
	private String foodChoiceFlag;
	private Integer reBookingFlag;
	private String availabilityStatus;
	private String clusterFlag;
	private String clusterMainLeg;
	private String srcStn;
	private String destStn;
	private String clusterPnrNo;
	private String clusterReservationId;

	private int noChild;
	private int noAdult;
	private int noSenior;
	private int noInfant;
	private int noWSenior;
	private String journeyDate;
	private Integer journeyClass;
	private String jrnyQuota;
	private String reqId;
	private String groupId;
	private BigInteger userId;
	private Integer bookingSeqNo;
	private Integer tktSeqNo;
	private String isCancelWaitListTicket;
	private String travelTypeId;
	private String trainJourneyClass;
	private String bookingConfig="";

	private Integer nadult;
	private Integer nchild;
	private Integer nsenior;
	private Integer ninfant;
	private Integer nwsenior;
	
}
