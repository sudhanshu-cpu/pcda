package com.pcda.login.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserResponse {

	private String errorMessage;
	private int errorCode;
	private String customMessage;
	private LoginUser response;
}
