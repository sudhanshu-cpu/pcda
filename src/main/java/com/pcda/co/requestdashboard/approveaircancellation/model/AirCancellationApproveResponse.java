package com.pcda.co.requestdashboard.approveaircancellation.model;

import java.util.List;

import lombok.Data;
@Data
public class AirCancellationApproveResponse {
	
	private String errorMessage;

	private Integer errorCode;

	private GetApproveAirCancellationModel response;

	private List<GetApproveAirCancellationModel> responseList;

}
