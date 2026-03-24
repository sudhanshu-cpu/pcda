package com.pcda.cgda.airtranscation.voucherdownload.model;

import lombok.Data;

@Data
public class VoucherResponse {

	private int errorCode ;
	private GetCountAmountModel response;	
	private String errorMessage;
}
