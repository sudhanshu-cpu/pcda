package com.pcda.mb.requestdashboard.offlinecancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetOfflineCancelResponse {

	private String errorMessage;
	private int errorCode;
	private String responseList;
	private List<String>response;
}


