package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetRailPreSearchResponse {
	private String errorMessage;
	private int errorCode;
	private List<GetRailPreSrchModel> responseList;
	private GetRailPreSrchModel response;

}
