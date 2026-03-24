package com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model;



import java.util.List;

import lombok.Data;

@Data
public class GenerateVoucherCgdaResponse {

	private String errorMessage;
    private int errorCode;
    private List<GetOutVoucherGenCgdaDataModel> responseList;
    private GetOutVoucherGenCgdaDataModel response;	
}
