package com.pcda.mb.requestdashboard.offlinecancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class OfflineCanPerViewResponse {
	private String errorMessage;

	private Integer errorCode;

	private GetPerOfflineCancelViewModel response;

	private List<GetPerOfflineCancelViewModel> responseList;
}
