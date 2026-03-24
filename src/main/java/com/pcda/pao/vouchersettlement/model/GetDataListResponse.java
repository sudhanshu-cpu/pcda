package com.pcda.pao.vouchersettlement.model;

import java.util.List;

import lombok.Data;

@Data
public class GetDataListResponse {

	private String errorMessage;
    private int errorCode;
    
    private List<GetVoucherSetListDataModel> responseList;
    
    private GetVoucherSetListDataModel response;	
    
   
	
}
