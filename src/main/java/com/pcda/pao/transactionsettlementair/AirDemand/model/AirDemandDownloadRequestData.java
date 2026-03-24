package com.pcda.pao.transactionsettlementair.AirDemand.model;

import lombok.Data;

@Data
public class AirDemandDownloadRequestData {

	private String downloadRequestId;
	private String fromDate;
	private String toDate;
	private Integer requestStatus;
	private String requestStatusStr;
	private String paoGroupId;
	private Integer fileDownloaded;
	private Integer masterMissingProcess;
	private Integer demandProcess;
	private Integer count;
	private String downloadDate;
	private String creationTime;
	private String serviceId;
	
	
}
