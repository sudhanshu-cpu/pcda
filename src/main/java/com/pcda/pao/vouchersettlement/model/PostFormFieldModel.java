package com.pcda.pao.vouchersettlement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostFormFieldModel {
	
	private String fromBookingDate="" ;
	private String toBookingDate="" ;
	private String voucherNo="" ;
	private String invoiceNo="" ;
	private String accountOffice="" ;
	
	private Integer statusType;
	private Integer serviceProvider ;

	

}
