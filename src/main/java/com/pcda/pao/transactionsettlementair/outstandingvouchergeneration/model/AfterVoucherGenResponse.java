package com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model;

import java.util.List;

import lombok.Data;

@Data
public class AfterVoucherGenResponse {

	

	private String errorMessage;
    private int errorCode;
    private List<AfterVoucherGenResponseModel> responseList;
    private AfterVoucherGenResponseModel response;
}
