package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class RailStationResponse {

	private String errorMessage;

	private int errorCode;

	private List<RailStation> responseList;

	private RailStation response;
}
