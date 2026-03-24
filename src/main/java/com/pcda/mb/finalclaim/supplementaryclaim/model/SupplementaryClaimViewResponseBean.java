package com.pcda.mb.finalclaim.supplementaryclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class SupplementaryClaimViewResponseBean {

	private String suppClaimId;
	private List<GradeBeanList> cityGradeList;
	private ExpenseDetails expenseDetails;
	private List<PayLevel> payLevel;
	private List<ClaimStatement> claimStatement;
	private List<ClaimDocument> claimDocument;
	private TravelContent travelContent;
	private PersonalInfo personalInfo;
	private List<TransportMode> transportModes;
	private Travellers travellers;
	private SuppEachRecord eachRecord;

}
