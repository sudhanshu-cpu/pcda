package com.pcda.cda.transactionsettlementair.vouchersettlement.model;



import java.util.List;

import lombok.Data;

@Data
public class AfterSaveCdaResponse {
	private String errorMessage;
    private int errorCode;
    
    private List<GetSaveResponseCdaParentModel> responseList;
    
    private GetSaveResponseCdaParentModel response;
}
