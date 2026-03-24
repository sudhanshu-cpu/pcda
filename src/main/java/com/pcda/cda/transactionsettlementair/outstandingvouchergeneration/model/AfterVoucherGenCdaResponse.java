package com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model;



import java.util.List;

import lombok.Data;

@Data
public class AfterVoucherGenCdaResponse {

	

	private String errorMessage;
    private int errorCode;
    private List<AfterVoucherGenCdaResponseModel> responseList;
    private AfterVoucherGenCdaResponseModel response;
}
