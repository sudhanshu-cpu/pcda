package com.pcda.adg.reports.claimstatusreport.model;

import lombok.Data;

@Data
public class ClaimStatusDataResBean {

	private String serviceId;
	private String serviceName;
	private String categoryId;
	private String categoryName;
	private Integer count;
	private String fromDate;
	private String toDate;
	
	
}
