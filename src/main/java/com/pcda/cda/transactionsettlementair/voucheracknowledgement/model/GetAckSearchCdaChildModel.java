package com.pcda.cda.transactionsettlementair.voucheracknowledgement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAckSearchCdaChildModel {

	private Integer paymentId;
	private String paymentAckStatus;
	private long amountPaid;
	private String paymentDate;
	private String paymentDesc;
}
