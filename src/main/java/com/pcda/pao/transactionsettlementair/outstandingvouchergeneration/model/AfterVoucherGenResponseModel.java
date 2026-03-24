package com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model;

import lombok.Data;

@Data
public class AfterVoucherGenResponseModel {

	private String message;
	private String voucherNo;
	private String creationDate;
	private String status;
	private String accountOfficeName;
	private Double totalDueAmount;
	private Double totalOutstandingAmount;
	
	
}
