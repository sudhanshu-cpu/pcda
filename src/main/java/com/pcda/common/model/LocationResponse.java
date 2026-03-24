package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class LocationResponse {

	private String errorMessage;

	private int errorCode;

	private List<Location> responseList;

	private Location response;

}
