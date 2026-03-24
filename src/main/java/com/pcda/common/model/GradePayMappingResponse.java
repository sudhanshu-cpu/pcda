package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class GradePayMappingResponse {

	private String errorMessage;

	private int errorCode;

	private List<GradePayMappingModel> responseList;

	private GradePayMappingModel response;

}
