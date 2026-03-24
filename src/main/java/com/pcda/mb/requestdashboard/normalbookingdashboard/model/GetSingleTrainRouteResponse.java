package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetSingleTrainRouteResponse {
	private String errorMessage;
	private int errorCode;
	private List<GetSingleTrainRouteModel> responseList;
	private GetSingleTrainRouteModel response;
}
