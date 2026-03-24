package com.pcda.pao.vouchersettlement.model;

import java.util.List;

import lombok.Data;

@Data
public class GetDataVoucherNoParentModel {

	
	private String  voucherNo;
	private String  creationTime;
	private String voucherStatus ;
	private long  totalAmount;
	private long  balanceTotalAmount;
	private long outstandingAmount ;
	private long balanceOutstandingAmount ;
	private String  remark;
	private Integer pdfGenerated  ;
	private String  filePath;
	private String balanceAvailable ;
	private List<GetDataFromVouchNoChildModel>  voucherDetails;

	
	
	
}
