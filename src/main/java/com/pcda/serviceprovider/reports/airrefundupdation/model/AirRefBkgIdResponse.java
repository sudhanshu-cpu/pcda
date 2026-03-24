package com.pcda.serviceprovider.reports.airrefundupdation.model;

import java.util.List;

import lombok.Data;

@Data
public class AirRefBkgIdResponse {

	private String errorMessage;
	private Integer errorCode;

	private List<GetAirRefParentModel> responseList;
	private GetAirRefParentModel response;
	
}
