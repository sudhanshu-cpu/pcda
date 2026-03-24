package com.pcda.mb.requestdashboard.aircancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAirCanDashDataResponse {

	private String errorMessage;
	private Integer errorCode;
	private GetAirCanDashParentModel response;
	private List<GetAirCanDashParentModel> responseList;
}
