package com.pcda.pao.reports.railbookingreport.model;

import java.util.List;

import lombok.Data;

@Data
public class GetBkgIdPopResponse {
	private int errorCode;
	private String errorMessage;
	private GetBkgIdPopParentModel response;
	private List<GetBkgIdPopParentModel>responseList;
}
