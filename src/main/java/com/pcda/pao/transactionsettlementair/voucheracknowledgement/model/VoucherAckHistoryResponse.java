package com.pcda.pao.transactionsettlementair.voucheracknowledgement.model;

import java.util.List;

import lombok.Data;

@Data
public class VoucherAckHistoryResponse {


	private int errorCode;
	private String errorMessage;
	private VoucherAckHistoryModel response;
	private List<VoucherAckHistoryModel>responseList;
	
}
