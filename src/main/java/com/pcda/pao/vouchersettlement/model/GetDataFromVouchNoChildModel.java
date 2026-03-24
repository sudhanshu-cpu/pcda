package com.pcda.pao.vouchersettlement.model;

import lombok.Data;

@Data
public class GetDataFromVouchNoChildModel {

	
	private String txnId;
	private Integer seqNo ;
	private String txnPaymentStatus;
	private long  outstandingAmount;
	private long dueAmount ;
	private String personalNo;
	private String unit ;
	private String travelerName ;
	

	
	
}
