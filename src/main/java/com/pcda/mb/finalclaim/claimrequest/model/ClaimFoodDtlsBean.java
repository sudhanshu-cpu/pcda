package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class ClaimFoodDtlsBean {

	private Integer rationCertiIssue;
	private Integer foodNoDay;
	private Double foodAmount;
	private Double actualFoodAmount;

	private Double oneDayMaxFoodAmnt;
	private Double oneDayRationMoney;

}
