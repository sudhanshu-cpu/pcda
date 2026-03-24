package com.pcda.cda.transactionsettlementair.vouchersettlement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetVoucherSetListDataCdaModel {
	
	
	private String voucherNo;
	private String creationTime;
	private String voucherStatus;
	private String balanceAvailable;
	private String filePath;
	private long totalAmount; 
	private long balanceTotalAmount; 
	private long outstandingAmount; 
	private long balanceOutstandingAmount; 
	private String remark;
	private Integer  pdfGenerated;
	private String voucherDetails;
	
  
 
  
   
	
}
