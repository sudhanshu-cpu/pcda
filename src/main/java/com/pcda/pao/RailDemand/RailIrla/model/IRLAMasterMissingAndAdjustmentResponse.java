package com.pcda.pao.RailDemand.RailIrla.model;

import java.util.List;

import lombok.Data;

@Data
public class IRLAMasterMissingAndAdjustmentResponse {
	private String errorMessage;
    private int errorCode;
    private IRLAMasterMissingDataModel response;
    private List<IRLAMasterMissingDataModel> responseList;
}
