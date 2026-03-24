package com.pcda.login.model;

import lombok.Data;

@Data
public class PreventProcessUpdateResponse {
	private String errorMessage;
	private int errorCode;
	private String customMessage;
	private String response;
}
