package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class RailFamilyTravellerDeatils {

	private String travellerName;
	private String relation;
	private Integer age;
	private String ticketNo;
	private Double passangerFare;
	private Double passRefundAmt;
	private Double passClaimedAmt;
	private Integer currentStatus;
	private Integer bookDtlsNo;

}
