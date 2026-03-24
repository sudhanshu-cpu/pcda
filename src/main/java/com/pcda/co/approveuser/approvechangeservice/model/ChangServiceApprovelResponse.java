package com.pcda.co.approveuser.approvechangeservice.model;

import java.util.List;

import lombok.Data;

@Data
public class ChangServiceApprovelResponse {


	private String errorMessage;

	private Integer errorCode;

	private GetChanSerApprovelModel response;

	private List<GetChanSerApprovelModel> responseList;
	
}
