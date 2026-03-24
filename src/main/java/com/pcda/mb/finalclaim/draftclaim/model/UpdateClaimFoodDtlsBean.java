package com.pcda.mb.finalclaim.draftclaim.model;

import lombok.Data;

@Data
public class UpdateClaimFoodDtlsBean {

	private Integer rationCertiIssue;
	private Integer foodNoDay;
	private Double foodAmount;
	private Double actualFoodAmount;
	private Double oneDayMaxFoodAmnt;
	private Double oneDayRationMoney;

}
