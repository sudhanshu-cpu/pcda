package com.pcda.cgda.transactionsettlementair.vouchersettlement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PostFormFieldCgdaModel {
	
	private String fromBookingDate="" ;
	private String toBookingDate="" ;
	private String voucherNo="" ;
	private String invoiceNo="" ;
	private String accountOffice="" ;
	
	private Integer statusType=null ;
	private Integer serviceProvider=null ;

	

}
