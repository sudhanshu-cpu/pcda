package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class PAOMapResponse {

	private String errorMessage;
	private int errorCode;
	private List<PAOMappingModel> responseList;
	private PAOMappingModel response;

}
