package com.pcda.cgda.transactionsettlementair.vouchersettlement.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetDataVoucherNoCgdaParentModel {

	
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
	private List<GetDataFromVouchNoCgdaChildModel>  voucherDetails;

	
	
	
}
