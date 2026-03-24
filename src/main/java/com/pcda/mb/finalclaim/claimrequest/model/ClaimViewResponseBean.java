package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.List;

import lombok.Data;

@Data
public class ClaimViewResponseBean {

	private Long loginUserId;

	List<GradeBeanList> gradeList;
	List<PayLevel> payLevel;
	List<ClaimStatement> claimStatement;
	List<ClaimDocument> claimDocument;
	List<TransportMode> transportModes;

	private ExpenseDetails expenseDetails;
	private TravelContent travelContent;
	private PersonalInfo personalInfo;
	private Travellers travellers;
	private Recoverydetails recoverydeatils;
	private EachRecord eachRecord;

	private String autoFill;

}
