package com.pcda.cda.transactionsettlementair.vouchersettlement.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetVoucherNoCdaResponse {

	
	private String errorMessage;
    private int errorCode;
    
    private List<GetDataVoucherNoCdaParentModel> responseList;
    
    private GetDataVoucherNoCdaParentModel response;	
}
