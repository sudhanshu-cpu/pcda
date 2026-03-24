package com.pcda.pao.reports.airdemandreport.model;

import lombok.Data;

@Data
public class PaoAirDmdStReportModel {
	
	private String irlaGroupId;
	private String irlaAccOffice;
	
	private Long totalDemand;
	private Double totalAmount;
	
	private Long downloadedDemand;
	private Double totaldemandAmount;
	
	private Long settledDemand;
	private Double totalSettleAmount;
	
	private Long pendingDwnldDemand;
	private Double totalDwnldPendingAmount;
	
	private Long pendingSettledDemand;
	private Double totalSettlePendingAmount;
	
	private String pendingDwnldDate;
	private String pendingSettledDate;

}


