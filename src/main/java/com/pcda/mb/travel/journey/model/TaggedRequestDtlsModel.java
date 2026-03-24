package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class TaggedRequestDtlsModel {

	private String journeyMode;
	private String trRuleID;
	private String requestID;
	private String isMixedModeRequest;
	private String refRequestId;
	private String oldDutyStn;
	private String newDutyStn;
	private String oldSPRNRS;
	private String newSprNrs;
	private String oldDutyNap;
	private String newDutyNap;
	private String oldSprNap;
	private String newSprNap;
	private String authorityDate;
	private String authorityNo;
	private String approvalState;
	private String jrnyDate;
	private String ltcYear;
	private String jrnyType;
	private int requestSeqNo;
	private String fromStnCode;
	private String toStnCode;
	private String fromStnName;
	private String toStnName;
	private String spouseNocNo;
	private String desinationStn;
	private String fromStn;
	
}
