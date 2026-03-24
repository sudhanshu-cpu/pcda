package com.pcda.pao.transactionsettlementair.voucheracknowledgement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAckSearchChildModel {

	private Integer paymentId;
	private String paymentAckStatus;
	private long amountPaid;
	private String paymentDate;
	private String paymentDesc;
}
