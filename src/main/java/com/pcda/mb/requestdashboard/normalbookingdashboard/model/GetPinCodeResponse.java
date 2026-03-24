package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetPinCodeResponse {

	private String errorMessage;
	private int errorCode;
	private List<PinCodeDetailsBean > responseList;
	private PinCodeDetailsBean  response;
}
