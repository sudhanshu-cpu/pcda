package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class GetRailPreSrchModel {

	private String isTatkalApproved;
	private boolean isCancelWaitListTicket;
	private String travelTypeId;
	private String personalNo;
	private String travelRuleId;
	private BigInteger userId;
	private int bookingSeqNo;
	private int tktSeqNo;
	private int legReBookingFlag;
	private String reqId;
	private String groupId;
	private String travellerName;
	private String srcStn;
	private String destStn;
	private int clusterFlag;
	private int clusterMainLeg;
	private String clusterPnrNo;
	private String clusterReservationId;
	private String clusterTrains;
	private boolean isFromStnGroupExist;
	private String reqFrmStnCode;
	private Map<String,String> groupedFromStation=new HashMap<>();
	private String frmStn;
	private boolean isToStnGroupExist;
	private String reqToStnCode;
	private Map<String,String> groupedToStation=new HashMap<>();
	private String toStn;
	private String quota;
	private int highestJrnyClass;
	private String dateStr;
	private String journeyDay;
	private String journeyMonth;
	private String journeyYear;
	private Map<Integer,String> jrnyClass=new HashMap<>();
	private int nAdult;
	private int nSenior;
	private int nChild;
	private int nWSenior;
	private int nInfant;
	private boolean isLadiesQuota;

	
}
