package com.pcda.mb.requestdashboard.railcancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAbortCancelResponse {

	private String errorMessage;
	private int errorCode;
	private List<String> responseList;
	private String response;
}
