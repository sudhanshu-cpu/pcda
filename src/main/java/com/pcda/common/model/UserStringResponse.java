package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class UserStringResponse {

	private String errorMessage;

	private int errorCode;

	private String response;

	private List<String> responseList;
}
