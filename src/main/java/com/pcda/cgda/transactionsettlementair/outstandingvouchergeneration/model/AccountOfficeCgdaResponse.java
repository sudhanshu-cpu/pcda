package com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model;


import java.util.Map;

import lombok.Data;

@Data
public class AccountOfficeCgdaResponse {
	private String errorMessage;
    private int errorCode;
    private Map<String,String> responseList;
    private AfterVoucherGenCgdaResponseModel response;
}
