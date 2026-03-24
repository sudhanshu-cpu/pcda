package com.pcda.mb.requestdashboard.mastermissingdashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class MasterMissingResponse {
	
	private String errorMessage;

	private int errorCode;

	private GetMasterMissingModel response;

	private List<GetMasterMissingModel> responseList;


}
