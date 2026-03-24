package com.pcda.cda.transactionsettlementair.voucheracknowledgement.model;


import java.util.Map;

import lombok.Data;

@Data
public class AccountOfficeAckResponse {
	private String errorMessage;
    private int errorCode;
    private Map<String,String> responseList;
    private String response;
}
