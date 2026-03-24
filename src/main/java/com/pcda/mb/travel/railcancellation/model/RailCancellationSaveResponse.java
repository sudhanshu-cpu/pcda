package com.pcda.mb.travel.railcancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class RailCancellationSaveResponse {

	private String errorMessage;

	private int errorCode;

	private PostRailCancellation response;

	private List<PostRailCancellation> responseList;
	
}
