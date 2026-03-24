package com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model;


import java.util.List;

import lombok.Data;

@Data
public class PostVoucherAckSPResponse {

	private int errorCode;
	private String errorMessage;
	private PostVoucherAckParentSPModel response;
	private List<PostVoucherAckParentSPModel>responseList;
}
