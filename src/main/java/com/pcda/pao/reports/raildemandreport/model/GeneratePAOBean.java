package com.pcda.pao.reports.raildemandreport.model;

import lombok.Data;

@Data
public class GeneratePAOBean {

	private String paoName;
	
	private Long totalDemand;
	private Double totalAmount;
	
	private Long demandDownload;
	private Double totaldemandAmount;
	
	private Long demandSettle;
	private Double totalSettleAmount;
	
	
	private Long downloadPending;
	private Double totalPendingAmount;
	
	private Long settlePending;
	private Double totalSettlePendingAmount;
	
	private String paoGroupId;
	private String downloadPendingDate;
	private String settlePendingDate;
	
}
