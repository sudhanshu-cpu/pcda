package com.pcda.cda.transactionsettlementair.voucheracknowledgement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoucherAckCdaHistoryModel {

	private int seqNo;
	private String ackNo;
	private String ackFilePath;
	private String paymentDate;
	private Double paidAmount;

}
