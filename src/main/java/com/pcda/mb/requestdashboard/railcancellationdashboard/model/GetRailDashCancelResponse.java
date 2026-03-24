package com.pcda.mb.requestdashboard.railcancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetRailDashCancelResponse {
	private String errorMessage;
	private int errorCode;
	private List<GetParentFinalCancelReqModel> responseList;
	private GetParentFinalCancelReqModel response;
}
