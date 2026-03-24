package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.Date;

import lombok.Data;

@Data
public class ClaimPassDtlsBean {

	private String travellerName;
	private String relation;
	private Integer age;
	private String ticketNo;
	private Double bookingAmount;
	private Double refundAmount;
	private Double claimedAmount;
	private Integer jrnyPerfmd;
	private Integer cancellationGrnd;
	private Date cancellationDate;
	private String cancellationRmrk;
	private Integer currentStatus;

}
