package com.pcda.cda.transactionsettlementair.voucheracknowledgement.model;

import java.util.List;

import lombok.Data;

@Data
public class VoucherAckCdaHistoryResponse {


	private int errorCode;
	private String errorMessage;
	private VoucherAckCdaHistoryModel response;
	private List<VoucherAckCdaHistoryModel>responseList;
	
}
