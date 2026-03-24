package com.pcda.mb.requestdashboard.aircancellationdashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class GetAirDashTktCancelResponse {

	private String errorMessage;
	private int errorCode;
	private String response;
	private List<String>responseList;
}
