package com.pcda.cgda.transactionsettlementair.vouchersettlement.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetVoucherNoCgdaResponse {

	
	private String errorMessage;
    private int errorCode;
    
    private List<GetDataVoucherNoCgdaParentModel> responseList;
    
    private GetDataVoucherNoCgdaParentModel response;	
}
