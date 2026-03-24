package com.pcda.cgda.airtranscation.voucherdownload.model;

import lombok.Data;

@Data
public class VoucherSettlePostResponseModel {

	private int errorCode;
	private String errorMessage;
	
	private VoucherSettlePostModel response;
}
