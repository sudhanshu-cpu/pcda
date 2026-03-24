package com.pcda.pao.vouchersettlement.model;

import lombok.Data;

@Data
public class GetSaveResponseChildModel {

	private String txnId;
	private String txnDate;
	private double txnAmount;
	private String personalNo;
	private String unitOfficeName;
	private String accountOfficeName;
	private String bookingId;
	private String txnStatus;	
	
}
