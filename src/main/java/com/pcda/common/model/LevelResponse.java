package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class LevelResponse {

	private String errorMessage;

	private int errorCode;

	private List<Level> responseList;

	private Level response;

}
