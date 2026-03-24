package com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AfterVoucherGenCgdaResponseModel {

	private String message;
	private String voucherNo;
	private String creationDate;
	private String status;
	private String accountOfficeName;
	private Double totalDueAmount;
	private Double totalOutstandingAmount;
	
	
}
