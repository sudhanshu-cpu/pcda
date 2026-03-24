package com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoucherAckHistorySPModel {

	private int seqNo;
	private String ackNo;
	private String ackFilePath;
	private String paymentDate;
	private Double paidAmount;

}
