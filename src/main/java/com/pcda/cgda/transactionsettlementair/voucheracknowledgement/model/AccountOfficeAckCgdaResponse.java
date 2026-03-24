package com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model;


import java.util.Map;

import lombok.Data;

@Data
public class AccountOfficeAckCgdaResponse {
	private String errorMessage;
    private int errorCode;
    private Map<String,String> responseList;
    private String response;
}
