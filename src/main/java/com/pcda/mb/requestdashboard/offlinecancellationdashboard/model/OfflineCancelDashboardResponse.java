package com.pcda.mb.requestdashboard.offlinecancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class OfflineCancelDashboardResponse {
	
	private String errorMessage;
	private int errorCode;
	private String response;
	private List<OfflineRequestBean>responseList;
}
