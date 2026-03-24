package com.pcda.common.model;

import java.util.Map;

import lombok.Data;

@Data
public class ContentResponse {

	private String errorMessage;
	private Integer errorCode;
	private Map<String, String> response;

}
