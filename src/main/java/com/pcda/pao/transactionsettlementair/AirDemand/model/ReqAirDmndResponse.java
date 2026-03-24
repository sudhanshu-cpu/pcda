package com.pcda.pao.transactionsettlementair.AirDemand.model;

import lombok.Data;

@Data
public class ReqAirDmndResponse {
	private String errorMessage;
	private Integer errorCode;
	private AirDemandResponseData response;
}
