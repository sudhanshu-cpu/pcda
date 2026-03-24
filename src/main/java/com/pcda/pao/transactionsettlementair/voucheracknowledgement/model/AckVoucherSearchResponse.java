package com.pcda.pao.transactionsettlementair.voucheracknowledgement.model;

import java.util.List;

import lombok.Data;

@Data
public class AckVoucherSearchResponse {

	private int errorCode;
	private String errorMessage;
	private GetAckSearchParentModel response;
	private List<GetAckSearchParentModel>responseList;
}
