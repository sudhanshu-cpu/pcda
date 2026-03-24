package com.pcda.mb.travel.journey.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TaggedRequestModel {

	private String message;
	private String currDutyStn;
	private String currSPRNRS;
	private String currDutyNAP;
	private String currSPRNAP;
	private String isLTCAvailedPrev;
	private String isLTCAvailedCurr;
	private String isLTCAvailedNext;
	private String allwdPrevYearOnw;
	private String allwdPrevYearRet;
	private String allwdCurrYearOnw;
	private String allwdCurrYearRet;
	private String allwdNextYearOnw;
	private String allwdNextYearRet;
	private String isRailRequestOnwardExist;
	private String isRailRequestReturnExist;
	private String isAirRequestOnwardExist;
	private String isAirRequestReturnExist;
	private String isAirOneWayExist;

	private Map<String, String> minOnwardJrnyDate;
	
	private List<TaggedRequestDtlsModel> taggedRequestDtls;
}
