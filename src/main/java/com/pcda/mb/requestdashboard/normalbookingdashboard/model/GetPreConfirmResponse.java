package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetPreConfirmResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetPreConfirmParentModel> responseList;
	private GetPreConfirmParentModel response;
}
