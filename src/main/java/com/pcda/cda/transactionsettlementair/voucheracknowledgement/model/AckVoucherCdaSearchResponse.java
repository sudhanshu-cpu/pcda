package com.pcda.cda.transactionsettlementair.voucheracknowledgement.model;

import java.util.List;

import lombok.Data;

@Data
public class AckVoucherCdaSearchResponse {

	private int errorCode;
	private String errorMessage;
	private GetAckSearchCdaParentModel response;
	private List<GetAckSearchCdaParentModel>responseList;
}
