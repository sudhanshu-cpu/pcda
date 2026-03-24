package com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model;



import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutVoucherGenCdaReqModel {

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
	private Integer serviceProvider=-1;
	private BigInteger loginUserId=BigInteger.ZERO;
	
	
	
}
