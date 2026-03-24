package com.pcda.pao.vouchersettlement.model;

import lombok.Data;

@Data
public class GetVoucherSetListDataModel {
	
	
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
