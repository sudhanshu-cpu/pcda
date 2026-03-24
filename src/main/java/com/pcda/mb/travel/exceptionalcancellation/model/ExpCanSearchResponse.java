package com.pcda.mb.travel.exceptionalcancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class ExpCanSearchResponse {


	private String errorMessage;

	private int errorCode;

	private GetSearchModel response;

	private List<GetSearchModel> responseList;
}
