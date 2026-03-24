package com.pcda.mb.finalclaim.rejectedclaim.model;

import lombok.Data;

@Data
public class RejectedFoodDtlsBean {

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
