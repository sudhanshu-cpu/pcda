package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DraftJourneyDetails {

	private List<DraftPassDtlsBean> draftPassDtlsBeans;

	private Integer seqNo;
	private String bookingId;
	private String bookingNo;
	private String travelMode;
	private String journeyClass;
	private Integer jrnyPerfmd;
	private Integer cancellationGrnd = 0;
	private String requestId;
	private String fromStation;
	private String toStation;
	private String journeyEndDate;
	private Double refundAmount;
	private String journeyDate;
	private Integer travelModeInt;

	private Date journeyStartDate;
	private String distance;
	private Double bookingAmount;
	private String journeyPerformed;
	private String serviceProvider;
	private Double claimedAmount;
	private String sector;
	private String journeyType;
	private Integer journeyTypeInt;
	private Date cancellationDate;
	private String cancellationRmrk;
	private Double sanctionAmount;
	private String deductionReason;

}