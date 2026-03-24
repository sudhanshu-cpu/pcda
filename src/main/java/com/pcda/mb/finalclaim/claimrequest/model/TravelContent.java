package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class TravelContent {

	private String personalNumber;
	private String travelId;
	private String travelStartDate;
	private String travelEndDate;
	private String travelTypeId;
	private String travelTypeName;
	private Double advanceAmount;
	private String travelOid;
	private Integer claimDocAllow;
	private String dtsDateOfAdv;
	private String dtsLuggageAdvAmt;
	private String dtsCoveyanceAdvAmt;
	private String dtsCTGAdvAmt;
	private String basicPay;
	private String transferType;

}
