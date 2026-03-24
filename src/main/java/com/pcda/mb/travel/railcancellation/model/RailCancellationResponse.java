package com.pcda.mb.travel.railcancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class RailCancellationResponse {
	private String errorMessage;

	private int errorCode;

	private RailCancellationModel response;

	private List<RailCancellationModel> responseList;

}
