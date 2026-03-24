package com.pcda.mb.requestdashboard.changeboardingstationdashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class BoardingStationDetailsResponse {
	
	private String errorMessage;

	private int errorCode;

	private BoardingStationDetailsModel response;

	private List<BoardingStationDetailsModel> responseList;
}
