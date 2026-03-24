package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class TravelTypeResponse {

	private String errorMessage;

	private int errorCode;

	private List<TravelType> responseList;

	private TravelType response;
}
