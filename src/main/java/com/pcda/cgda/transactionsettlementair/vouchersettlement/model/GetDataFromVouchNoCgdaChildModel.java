package com.pcda.cgda.transactionsettlementair.vouchersettlement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetDataFromVouchNoCgdaChildModel {

	
	private String txnId;
	private Integer seqNo ;
	private String txnPaymentStatus;
	private long  outstandingAmount;
	private long dueAmount ;
	private String personalNo;
	private String unit ;
	private String travelerName ;
	

	
	
}
