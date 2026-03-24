package com.pcda.pao.transactionsettlementair.AirDemand.model;

import java.util.List;

import lombok.Data;

@Data
public class AirDemandMasterMissingResponse {
	private String errorMessage;
    private int errorCode;
    private AirMasterMissingDataModel response;
    private List<AirMasterMissingDataModel> responseList;
}
