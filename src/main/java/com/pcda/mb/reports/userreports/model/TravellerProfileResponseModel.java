package com.pcda.mb.reports.userreports.model;

import java.util.List;

import lombok.Data;

@Data
public class TravellerProfileResponseModel {
	
	private String errorMessage;

	private Integer errorCode;

	private GetTravllerProfileModel response;

	private List<GetTravllerProfileModel> responseList;
	
	

}
