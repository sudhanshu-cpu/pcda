package com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model;


import java.util.List;

import lombok.Data;

@Data
public class AckVoucherSearchSPResponse {

	private int errorCode;
	private String errorMessage;
	private GetAckSearchParentSPModel response;
	private List<GetAckSearchParentSPModel>responseList;
}
