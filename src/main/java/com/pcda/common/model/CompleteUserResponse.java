package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class CompleteUserResponse {

	private String errorMessage;

	private int errorCode;

	private VisitorModel response;

	private List<VisitorModel> responseList;
}
