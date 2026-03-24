package com.pcda.mb.reports.claimreport.model;

import lombok.Data;

@Data
public class ClaimReportModel {
	private String claimId;
	private String personalNo;
	private String approvalState;
	private Double totalAdvanceAmount;
	private Double totalClaimAmount;
	private Double totalSpentAmount;
	private Double refundAmount;
	private String accountOfficeName;
	private String travelID;
	private String travelTypeName;
}
