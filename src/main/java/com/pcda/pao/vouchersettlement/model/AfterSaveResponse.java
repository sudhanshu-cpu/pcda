package com.pcda.pao.vouchersettlement.model;



import java.util.List;

import lombok.Data;

@Data
public class AfterSaveResponse {
	private String errorMessage;
    private int errorCode;
    
    private List<GetSaveResponseParentModel> responseList;
    
    private GetSaveResponseParentModel response;
}
