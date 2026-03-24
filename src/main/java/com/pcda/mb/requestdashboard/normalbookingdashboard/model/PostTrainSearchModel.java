package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostTrainSearchModel {

	private String frmStation;
	private String toStation;
	private String frmStationCode;
	private String toStationCode;
	private String journeyDate;
	private int journeyClass;
	private String isTatkalApproved;
	private String isTatkal;
	private int nChild;
	private int nAdult;
	private int nSenior;
	private int nInfant;
	private int nWSenior;
	private int totalNoOfPassenger;
	private String srcStn;
	private String destStn;
	private int clusterFlag;
	private int clusterMainLeg;
	private String clusterTrains;
	private String clusterPnrNo;
	private String clusterReservationId;
	private String reqId;
	private int highestJrnyClass;
	private String groupId;
	private BigInteger userId;
	private int bookingSeqNo;
	private int tktSeqNo;
	private String isCancelWaitListTicket;
	private String appDepDate;
	private String travelTypeId;
	
	private String travelRuleId;
	private String personalNo;
	
	private int reBookingFlag;
	
	private String journeyClassVal;
	private String bookingType;
	private String isReBookingCase;

}
