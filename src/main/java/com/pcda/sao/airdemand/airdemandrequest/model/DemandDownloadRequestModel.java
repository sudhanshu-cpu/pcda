package com.pcda.sao.airdemand.airdemandrequest.model;

import lombok.Data;

@Data
public class DemandDownloadRequestModel {

	private String dwnReqId;
	private String fromDate;
	private String toDate;
	private Integer reqStatus;
	private String reqStatusStr;
	private String groupId;
	private Integer isFileDwn;
	private String filePath;
	private long count;
	private String creationTime;
	private String dwnDate;
	
	
	
	
}
