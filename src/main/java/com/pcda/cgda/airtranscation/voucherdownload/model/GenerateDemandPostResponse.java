package com.pcda.cgda.airtranscation.voucherdownload.model;

import lombok.Data;

@Data
public class GenerateDemandPostResponse {

	private String errorMessage;
	private int errorCode;
	private String request;
	private String requestType;
	private String customMessage;
	private GenerateDemandPostModel response;
}
