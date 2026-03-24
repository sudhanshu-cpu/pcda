package com.pcda.mb.requestdashboard.aircancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAirCanDashBkgIdResponse {

	private String errorMessage;
	private Integer errorCode;
	private GetAirDashBkgIdParentModel response;
	private List<GetAirDashBkgIdParentModel> responseList;
}
