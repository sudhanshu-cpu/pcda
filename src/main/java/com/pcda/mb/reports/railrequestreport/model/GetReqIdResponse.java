package com.pcda.mb.reports.railrequestreport.model;

import java.util.List;

import lombok.Data;

@Data
public class GetReqIdResponse {
	
	private String errorMessage;
	private int errorCode;
	private GetRailReqIdParentModel response;
	private List<GetRailReqIdParentModel> responseList;

}
