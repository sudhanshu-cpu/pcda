package com.pcda.pao.RailDemand.RailIrla.model;

import lombok.Data;

@Data
public class IRLADownloadRequestData {
	
	private String downloadRequestId;
	private String fromDate;
	private String toDate;
	private Integer requestStatus;
	private String requestStatusStr;
	private String paoGroupId;
	private Integer fileDownloaded;
	private Integer masterMissingProcess;
	private Integer adjustmentProcess;
	private String filePath;
	private Integer irlaCount;
	private String adjustmentDate;
	private String creationTime;
	private String serviceId;
}
