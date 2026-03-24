package com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model;



import java.util.List;

import lombok.Data;

@Data
public class GenerateVoucherCdaResponse {

	private String errorMessage;
    private int errorCode;
    private List<GetOutVoucherGenCdaDataModel> responseList;
    private GetOutVoucherGenCdaDataModel response;	
}
