package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class TravelRequestViewResponse {

	private String errorMessage;
	private Integer errorCode;
	private TravelRequestViewBean response;

}
