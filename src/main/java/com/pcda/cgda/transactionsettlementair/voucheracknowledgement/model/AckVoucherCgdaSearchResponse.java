package com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model;

import java.util.List;

import lombok.Data;

@Data
public class AckVoucherCgdaSearchResponse {

	private int errorCode;
	private String errorMessage;
	private GetAckSearchCgdaParentModel response;
	private List<GetAckSearchCgdaParentModel>responseList;
}
