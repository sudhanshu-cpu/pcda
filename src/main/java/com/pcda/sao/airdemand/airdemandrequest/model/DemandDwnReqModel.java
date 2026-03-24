package com.pcda.sao.airdemand.airdemandrequest.model;

import java.util.List;

import lombok.Data;

@Data
public class DemandDwnReqModel {
	
	private List<DemandDownloadRequestModel> downloadRequests;
	private String startDate;
	private Integer drRecordsCount;	
}
