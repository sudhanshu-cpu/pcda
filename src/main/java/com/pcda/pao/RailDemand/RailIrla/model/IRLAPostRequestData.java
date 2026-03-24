package com.pcda.pao.RailDemand.RailIrla.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class IRLAPostRequestData {

	private String fromDate;
	private String toDate;
	private BigInteger loginUserId;
	private String serviceId;
	
}
