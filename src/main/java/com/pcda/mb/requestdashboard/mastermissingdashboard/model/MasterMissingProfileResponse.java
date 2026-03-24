package com.pcda.mb.requestdashboard.mastermissingdashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class MasterMissingProfileResponse {
	
	private String errorMessage;

	private int errorCode;

	private GetMasterMissingProfileModel response;

	private List<GetMasterMissingProfileModel> responseList;

}
