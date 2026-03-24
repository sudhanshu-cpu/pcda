package com.pcda.cgda.airtranscation.voucherdownload.model;

import lombok.Data;

@Data
public class VocuherPostResponseModel {

	private int errorCode;
	private String errorMessage;
	private VoucherPostModel response;
	
}
