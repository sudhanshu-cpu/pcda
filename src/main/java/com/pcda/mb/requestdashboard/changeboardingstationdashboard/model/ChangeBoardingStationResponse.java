package com.pcda.mb.requestdashboard.changeboardingstationdashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class ChangeBoardingStationResponse {
	
	private String errorMessage;

	private int errorCode;

	private ChangeBoardingStationResponseModel response;

	private List<ChangeBoardingStationResponseModel> responseList;

}
