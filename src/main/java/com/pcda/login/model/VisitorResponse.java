package com.pcda.login.model;

import com.pcda.common.model.VisitorModel;

import lombok.Data;

@Data
public class VisitorResponse {


	private String errorMessage;
	private int errorCode;
	private String customMessage;
	private VisitorModel response;
}
