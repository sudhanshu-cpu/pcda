package com.pcda.pao.transactionsettlementair.voucheracknowledgement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoucherAckHistoryModel {

	private int seqNo;
	private String ackNo;
	private String ackFilePath;
	private String paymentDate;
	private Double paidAmount;

}
