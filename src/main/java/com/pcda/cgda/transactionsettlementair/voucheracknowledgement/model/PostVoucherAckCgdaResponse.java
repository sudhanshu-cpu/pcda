package com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model;

import java.util.List;

import lombok.Data;

@Data
public class PostVoucherAckCgdaResponse {

	private int errorCode;
	private String errorMessage;
	private PostVoucherAckCgdaParentModel response;
	private List<PostVoucherAckCgdaParentModel>responseList;
}
