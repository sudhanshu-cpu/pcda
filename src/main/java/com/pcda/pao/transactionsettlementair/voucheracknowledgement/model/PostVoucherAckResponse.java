package com.pcda.pao.transactionsettlementair.voucheracknowledgement.model;

import java.util.List;

import lombok.Data;

@Data
public class PostVoucherAckResponse {

	private int errorCode;
	private String errorMessage;
	private PostVoucherAckParentModel response;
	private List<PostVoucherAckParentModel>responseList;
}
