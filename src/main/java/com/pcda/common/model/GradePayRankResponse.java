package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class GradePayRankResponse {

	private String errorMessage;

	private int errorCode;

	private List<GradePayRankModel> responseList;

	private GradePayRankModel response;

}
