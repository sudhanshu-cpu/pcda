package com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model;

import java.util.List;

import lombok.Data;

@Data
public class VoucherAckCgdaHistoryResponse {


	private int errorCode;
	private String errorMessage;
	private VoucherAckCgdaHistoryModel response;
	private List<VoucherAckCgdaHistoryModel>responseList;
	
}
