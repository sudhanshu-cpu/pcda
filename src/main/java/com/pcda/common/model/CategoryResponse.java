package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class CategoryResponse {

	private String errorMessage;

	private int errorCode;

	private List<Category> responseList;

	private Category response;

}
