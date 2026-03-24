package com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutVoucherGenReqModel {

	private String fromBookingDate="";
	private String toBookingDate="";
	private String bookingId="";
	private String txnId="";
	private String serviceType="";
	
	private String personalNo="";
	private String travelType="";
	private String accountOffice="";
	private String statusType="";
	
	private String unitOffice="";
	private Integer serviceProvider;
	private BigInteger loginUserId;
	
	
	
}
