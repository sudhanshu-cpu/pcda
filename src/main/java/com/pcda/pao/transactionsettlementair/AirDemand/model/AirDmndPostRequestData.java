package com.pcda.pao.transactionsettlementair.AirDemand.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class AirDmndPostRequestData {

	private String fromDate;
	private String toDate;
	private BigInteger loginUserId;
	private String serviceId;
	
}
