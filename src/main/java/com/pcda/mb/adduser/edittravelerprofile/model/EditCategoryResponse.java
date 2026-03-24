package com.pcda.mb.adduser.edittravelerprofile.model;

import java.util.List;

import lombok.Data;

@Data
public class EditCategoryResponse {

	private String errorMessage;

	private Integer errorCode;

	private LevelInfo response;

	private List<LevelInfo> responseList;
}
