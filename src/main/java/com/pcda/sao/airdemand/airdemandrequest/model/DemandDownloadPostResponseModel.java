package com.pcda.sao.airdemand.airdemandrequest.model;

import lombok.Data;

@Data
public class DemandDownloadPostResponseModel {
	private int errorCode;
	private String errorMessage;
	private DemandDownloadPostModel response;
}
