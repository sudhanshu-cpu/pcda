package com.pcda.login.model;

import java.util.List;

import lombok.Data;

@Data
public class GetSessionsResponse {
	private String errorMessage;
	private int errorCode;
	private String customMessage;
	private GetSessionModel response;
	private List<GetSessionModel> responseList;
}
