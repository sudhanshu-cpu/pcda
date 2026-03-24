package com.pcda.co.requestdashboard.approverailcancellation.model;



import java.util.List;

import lombok.Data;

@Data
public class RailCanApproveBookingResponse {

	private String errorMessage;

	private int errorCode;

	private GetApproveCancelParentModel response;

	private List<GetApproveCancelParentModel> responseList;
	
}
