package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class CodeHeadResponse {
	
	private String errorMessage;
	private int errorCode;
	private List<CodeHead> responseList;
	private CodeHead response;


}
