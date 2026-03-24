package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAirBookResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetAirBookReqIdParent> responseList;
	private GetAirBookReqIdParent response;
}
