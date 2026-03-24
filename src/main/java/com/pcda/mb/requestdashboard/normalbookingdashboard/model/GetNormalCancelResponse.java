package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class GetNormalCancelResponse {

	private int errorCode;
	private String errorMessage;
	private String response;
	private List<String> responseList;
}
