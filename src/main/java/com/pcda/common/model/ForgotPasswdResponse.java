package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class ForgotPasswdResponse {

	private String errorMessage;

	private int errorCode;

	private GetForgotPassPnoInfo response;

	private List<GetForgotPassPnoInfo> responseList;
}
