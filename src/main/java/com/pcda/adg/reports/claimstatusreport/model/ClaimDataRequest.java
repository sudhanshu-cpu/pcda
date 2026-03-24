package com.pcda.adg.reports.claimstatusreport.model;

import lombok.Data;

@Data
public class ClaimDataRequest {
	private String fromDate;
	private String toDate;
	private String fromDateStr;
	private String toDateStr;
	private String userServiceId;
	private String categoryId;
	
	

}
