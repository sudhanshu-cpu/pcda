package com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PopUpParamCdaModel {

	private Integer seqNo;
	private String personalNo;
	private String transactionId;

	private String netOutstanding;

	private String dueAmount;

	private String netOutstandingTotal;
	private String dueAmountTotal;
}
