package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {

	private String errorMessage;

	private int errorCode;

	private User response;

	private List<User> responseList;

}
