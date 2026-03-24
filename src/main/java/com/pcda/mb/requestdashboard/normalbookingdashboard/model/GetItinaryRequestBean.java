package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.math.BigInteger;
import java.util.Map;

import lombok.Data;

@Data
public class GetItinaryRequestBean {
	private String isShowChangeStnVal;
	private String trainCategory;
	private BigInteger userId;
	private Integer bookingSeqNo;
	private String isCancelWaitListTicket;
	private String reqId;
	private String travelTypeId;
	private String groupId;
	private Integer totalNoOfpxn;
	private Integer nAdult;
	private Integer nChild;
	private Integer nSenior;
	private Integer nWSenior;
	private Integer nInfant;
	private String quota;
	private String trainNo;
	private String trainName;
	private String journeyClass;
	private String isChildHalfFareApplicable;
	private String journeyDate;
	private String srcStnCode;
	private String desStnCode;
	private String srcStnName;
	private String depTimeSrc;
	private String desStnName;
	private String arrTimeDest;
	private String trainTypeVal;
	private String foodChoiceFlag;
	private Integer reBookingFlag;
	private String availabilityStatus;
	private String isAvailabilityStatus;
	private Integer clusterFlag;
	private Integer clusterMainLeg;
	private String clusterSrcStn;
	private String clusterDestStn;
	private String clusterPnrNo;
	private String clusterReservationId;
	private String jrnyClass;
	private Map<String,String> boardingStationMap;

}
