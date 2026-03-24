package com.pcda.cgda.transactionsettlementair.vouchersettlement.model;



import java.util.List;

import lombok.Data;

@Data
public class AfterSaveCgdaResponse {
	private String errorMessage;
    private int errorCode;
    
    private List<GetSaveResponseCgdaParentModel> responseList;
    
    private GetSaveResponseCgdaParentModel response;
}
