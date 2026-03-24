package com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model;

import lombok.Data;

@Data
public class PopUpParamModel {

	private Integer seqNo;
	private String personalNo;
	private String transactionId;

	private String netOutstanding;

	private String dueAmount;

	private String netOutstandingTotal;
	private String dueAmountTotal;
}
