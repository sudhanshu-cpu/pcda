package com.pcda.mb.finalclaim.claimrequest.model;

import com.pcda.util.RelationType;

import lombok.Data;

@Data
public class FamilyTravellerDeatils {

	private String travellerName;
	private RelationType relation;
	private Integer age;
	private String ticketNo;
	private Double passangerFare;
	private Double passRefundAmt;
	private Double passClaimedAmt;
	private Integer currentStatus;
	private Integer bookDtlsNo;

}
