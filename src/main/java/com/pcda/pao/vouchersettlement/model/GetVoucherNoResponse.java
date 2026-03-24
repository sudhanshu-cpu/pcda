package com.pcda.pao.vouchersettlement.model;

import java.util.List;

import lombok.Data;

@Data
public class GetVoucherNoResponse {

	
	private String errorMessage;
    private int errorCode;
    
    private List<GetDataVoucherNoParentModel> responseList;
    
    private GetDataVoucherNoParentModel response;	
}
