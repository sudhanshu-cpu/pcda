package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetFareAccomodateResponse {
	private String errorMessage;
	private Integer errorCode;
	private List<GetFareAccomodateParent> responseList;
	private GetFareAccomodateParent response;
}
