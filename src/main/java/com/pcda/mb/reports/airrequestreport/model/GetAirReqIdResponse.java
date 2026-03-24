package com.pcda.mb.reports.airrequestreport.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAirReqIdResponse {

	private int errorCode;
	private String message;
	private GetAirRqIdParentModel response;
	private List<GetAirRqIdParentModel> responseList;
}
