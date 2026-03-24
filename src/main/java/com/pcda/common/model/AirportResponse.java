package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class AirportResponse {

	private String errorMessage;

	private int errorCode;

	private List<AirPort> responseList;

	private AirPort response;
	
}
