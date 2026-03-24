package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetTrainSearchResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetRailSearchParentModel> responseList;
	private GetRailSearchParentModel response;

}
