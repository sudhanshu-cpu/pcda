package com.pcda.mb.finalclaim.draftclaim.model;

import lombok.Data;

@Data
public class DraftPassDtlsBean {

	private Integer cancellationGrnd;
	private String cancellationDate;
	private String cancellationRmrk;
	private Integer journeyPerformed;
	private Double bookingAmount;
	private Double refundAmount;
	private Double claimedAmount;
	private String ticketNo;
	private Integer age;
	private String relation;
	private String travellerName;
	private Integer passSeq;
	private Integer currentStatus;

}
