package com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AfterVoucherGenCdaResponseModel {

	private String message;
	private String voucherNo;
	private String creationDate;
	private String status;
	private String accountOfficeName;
	private Double totalDueAmount;
	private Double totalOutstandingAmount;
	
	
}
