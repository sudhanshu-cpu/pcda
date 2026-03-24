package com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model;


import java.util.List;

import lombok.Data;

@Data
public class VoucherAckHistorySPResponse {


	private int errorCode;
	private String errorMessage;
	private VoucherAckHistorySPModel response;
	private List<VoucherAckHistorySPModel>responseList;
	
}
