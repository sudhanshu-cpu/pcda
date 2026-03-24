package com.pcda.cgda.transactionsettlementair.vouchersettlement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetSaveResponseCgdaChildModel {

	private String txnId;
	private String txnDate;
	private double txnAmount;
	private String personalNo;
	private String unitOfficeName;
	private String accountOfficeName;
	private String bookingId;
	private String txnStatus;	
	
}
