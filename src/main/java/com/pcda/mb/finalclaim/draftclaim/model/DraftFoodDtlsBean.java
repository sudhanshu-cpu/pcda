package com.pcda.mb.finalclaim.draftclaim.model;

import lombok.Data;

@Data
public class DraftFoodDtlsBean {

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
