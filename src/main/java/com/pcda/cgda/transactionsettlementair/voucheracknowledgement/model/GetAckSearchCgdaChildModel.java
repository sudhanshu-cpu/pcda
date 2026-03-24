package com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAckSearchCgdaChildModel {

	private Integer paymentId;
	private String paymentAckStatus;
	private long amountPaid;
	private String paymentDate;
	private String paymentDesc;
}
