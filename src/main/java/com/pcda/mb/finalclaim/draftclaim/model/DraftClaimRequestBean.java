package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class DraftClaimRequestBean {

	private String claimTravelTypeId;
	private List<GradeBeanList> cityGradeList;
	private ExpenseDetails expenseDetails;
	private Integer armedfrc;
	private List<PayLevel> payLevel;
	private Recoverydetails recoverydeatils;
	private List<TransportMode> transportModes;
	private Travellers travellers;
	private DraftTaDaClaimDetails draftTaDaClaimDetails;

}
