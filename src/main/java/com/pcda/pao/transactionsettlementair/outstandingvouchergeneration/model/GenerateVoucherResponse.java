package com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model;

import java.util.List;

import lombok.Data;

@Data
public class GenerateVoucherResponse {

	private String errorMessage;
    private int errorCode;
    private List<GetOutVoucherGenDataModel> responseList;
    private GetOutVoucherGenDataModel response;	
}
