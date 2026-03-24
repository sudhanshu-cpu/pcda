package com.pcda.mb.requestdashboard.offlinecancellationdashboard.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class OfflineRequestBean {
	
	private Integer serialNo;
	private String requestId;
	private String travelId;
	private BigInteger userId;
	private String personalNo;
	private String empCode;
	private String travelTypeId;	
	private Date creationTime;
	private String bookingType;
	private String isTatkalApproved;	
	private String sequenceNo;	
	private String tktSequenceNo;
	private String approvalStateStr;
	private String  originStation;
	private String destnStation;
	private String originStnName;
	private String destnStnName;
	private Date journyDate;
	private String fulHalfTkt;	
	private String encryptedReqId;
	private String adultChild;
	private String requestType;
	private String dateFormateJurny;

}
