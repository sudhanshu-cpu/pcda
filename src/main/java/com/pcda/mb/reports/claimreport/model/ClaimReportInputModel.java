package com.pcda.mb.reports.claimreport.model;

import lombok.Data;

@Data
public class ClaimReportInputModel {

	
	private String claimId="";
	private String personalNo="";
	private String travelType="";
	private String accountOffice="";
	private String groupId;
}
