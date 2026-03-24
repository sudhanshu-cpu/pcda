package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class GetRailSearchParentModel {

	private String isTatkalApproved;
	private String isTatkal;
	private String appDepDate;
	private BigInteger userId;
	private String travelTypeId;
	private String isCancelWaitListTicket;
	private int bookingSeqNo;
	private int tktSeqNo;
	private String reqId;
	private String groupId;
	private int totalNoOfPassenger;
	private int highestJrnyClass;
	private int clusterFlag;
	private int clusterMainLeg;
	private String srcStn;
	private String destStn;
	private String clusterTrains;
	private String clusterPnrNo;
	private String clusterReservationId;
	private String reqFrmStnCode;
	private Map<String,String> groupedFromStation=new HashMap<>();
	private String frmStationName;
	private String frmStation;
	private boolean isToStnGroupExist;
	private String reqToStnCode;
	private Map<String,String> groupedToStation=new HashMap<>();
	private String toStaionName;
	private String toStaion;
	private String journeyDate;
	private String jrnyQuota;
	private int journeyClass;
	private String journeyClassVal;
	private int isLegReBooking;
	private int noOfAdult;
	private int noOfChild;
	private int noOfSenior;
	private int noOfInfant;
	private int noOfWSenior;
	private Map<Integer,String> jrnyClass=new HashMap<>();
	private boolean isFromStnGroupExist;
	
	private List<Trainbtwnstnslist> responseBean;
	private String errorMessage;
	 private String errorCode;


}
