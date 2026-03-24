package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class DraftNonDtsJourneyDetails {

	private List<DraftPassDtlsBean> draftPassDtlsBeans;

	private Integer seqNo;
	private String fromPlace;
	private String toPlace;
	private String modeOfTravel;
	private String travelClass;
	private String journeyStartTime;
	private String journeyEndTime;
	private String distance;
	private String jrnyRefNo;
	private Double amount;
	private Double refundAmount;
	private Integer journeyType;
	private Integer journeyPerformed;
	private Integer cancellationGrnd = 0;
	private String cancellationDate;
	private String cancellationRmrk;
	private Double claimedAmount;
	private String sector;

}
