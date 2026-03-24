package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class PostIRSearchConfirmModel {

	private String ticketChoice;
	private String journeyAddType;
	private String addMobileNo;
	private String houseNo;
	private String street;
	private String village;
	private String pinCode;
	private String district;
	private String stateName;
	private String postOffice;
	private String addUnitName;
	private String addressType;
	private String unitPin;
	private String reqId;
	private String groupId;
	private Integer bookingSeqNo;
	private Integer adult;
	private Integer child;
	private Integer senior;
	private Integer wsenior;
	private Integer infant;
	private Integer clusterFlag;
	private Integer clusterMainLeg;
	private String trainNo;
	private String clusterSrcStn;
	private String clusterDestStn;
	private String destStnCode;
	private String jrnyClass;
	private String originStnCode;
	private String jrnyDate;
	private String jrnyQuota;
	private String isLadiesQuotaVal;
	private String trainCategory;
	private BigInteger userId;
	private String seniorCitizenApplicable;
	private String resChoice;
	private String boardingPointStnCode;
	
	private String isClassUpgrade;
	private String isCoachChoiceOpted;
	private String coachId;
	private String onlyConfirmBerths;
	private String isTicketChoiceSameCoach;
	private String trainTypeVal;
	private String trainName;
	private String availabilityStatus;
	private String reservationUptoStnCode;
	private Integer isReBookingFlag=0;
	
	private String accountOffice;
	private String ipAddress;
	private String sessionId;
	private BigInteger loginUserId;
	private String pxnMobileNo;
	
	
	private List<PostBookingPassangeDetails> pxnsDtls;

}
