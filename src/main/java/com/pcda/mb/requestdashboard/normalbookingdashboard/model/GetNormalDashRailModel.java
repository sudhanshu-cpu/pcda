package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data

public class GetNormalDashRailModel {

	
	private String requestFor;
	private Integer serialNo;
	private String railRequestId;
	private String travelId;
	private BigInteger userId;
	private String personalNo;
	private String travelTypeId;	
	private Date creationTime;
	private String bookingType;
	private String isTatkalApproved;
	private String clusterFlag;
	private String clusterMainLeg;
	private String sequenceNo;	
	private String tktSequenceNo;
	private String approvalStateStr;
	private String  originStation;
	private String destnStation;
	private String originStnName;
	private String destnStnName;
	private Date journyDate;
	private String fulHalfTkt;
	private Double daAdvanceAmt;
	private String encryptedReqId;
	private String adultChild;
	private String pnrNumberGenerated;
	private String requestType;
    private String journeyDateStr;
	
}
