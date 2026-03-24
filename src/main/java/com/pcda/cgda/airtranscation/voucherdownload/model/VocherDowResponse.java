package com.pcda.cgda.airtranscation.voucherdownload.model;

import java.util.List;

import lombok.Data;

@Data
public class VocherDowResponse {

	private List<VoucherReqModel> responseList;
	private VoucherReqModel response;
	private int errorCode;
	private String errorMessage;
	
	
	
}
