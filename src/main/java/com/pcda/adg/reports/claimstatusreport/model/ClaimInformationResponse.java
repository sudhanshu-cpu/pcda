package com.pcda.adg.reports.claimstatusreport.model;

import lombok.Data;

@Data
public class ClaimInformationResponse {

	private String claimId;
	private String personalNo;
	private String travelId;
	private String submittedDate;
	private String name;
}
