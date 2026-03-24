package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class EnumTypeResponse {

	private String errorMessage;
	private int errorCode;
	private List<EnumType> responseList;
	private EnumType response;

}
