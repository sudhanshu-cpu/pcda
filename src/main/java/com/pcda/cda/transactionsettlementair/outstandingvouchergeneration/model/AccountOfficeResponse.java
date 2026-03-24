package com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model;


import java.util.Map;

import lombok.Data;

@Data
public class AccountOfficeResponse {
	private String errorMessage;
    private int errorCode;
    private Map<String,String> responseList;
    private AfterVoucherGenCdaResponseModel response;
}
