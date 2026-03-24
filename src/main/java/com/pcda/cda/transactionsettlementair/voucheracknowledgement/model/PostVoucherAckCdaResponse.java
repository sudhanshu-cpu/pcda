package com.pcda.cda.transactionsettlementair.voucheracknowledgement.model;

import java.util.List;

import lombok.Data;

@Data
public class PostVoucherAckCdaResponse {

	private int errorCode;
	private String errorMessage;
	private PostVoucherAckCdaParentModel response;
	private List<PostVoucherAckCdaParentModel>responseList;
}
