package com.pcda.mb.travel.emailticket.model;

import java.util.List;

import lombok.Data;

@Data
public class ViewTravelDtlResponse {

	private String errorMessage;

	private Integer errorCode;

	private ViewTravelDtl response;

	private List<ViewTravelDtl> responseList;
	
}
