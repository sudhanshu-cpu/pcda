package com.pcda.cda.transactionsettlementair.vouchersettlement.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetDataListCdaResponse {

	private String errorMessage;
    private int errorCode;
    
    private List<GetVoucherSetListDataCdaModel> responseList;
    
    private GetVoucherSetListDataCdaModel response;	
    
   
	
}
