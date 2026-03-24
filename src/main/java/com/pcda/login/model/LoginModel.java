package com.pcda.login.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginModel {

	private String userAlias;
	private String password;
}
