package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class GetNormalDashDataResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetNormalDashParentModel> responseList;
	private GetNormalDashParentModel response;
}
