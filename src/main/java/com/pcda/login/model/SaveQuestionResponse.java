package com.pcda.login.model;

import com.pcda.common.model.SecurityQuestionModel;

import lombok.Data;

@Data
public class SaveQuestionResponse {
	private String errorMessage;
	private int errorCode;
	private String customMessage;
	private SecurityQuestionModel response;
}
