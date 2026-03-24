package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class ViewClaimFoodDtlsBean {

	private Integer rationCertiIssue;
	private Integer noOfDay;
	private Double billAmount;
	private Double sanctionbillAmount;
	private String deductionReason;
	private Double actualBillAmount;
	private Double oneDayMaxFoodAmnt;
	private Double oneDayRationMoney;

	private Integer seqNo;

}
