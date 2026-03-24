package com.pcda.co.requestdashboard.approverailcancellation.model;



import java.util.List;

import lombok.Data;

@Data
public class RailCanApprovalResponse {

	private String errorMessage;
    private int errorCode;
    private List<GetApprovalDataModel> responseList;
    private GetApprovalDataModel response;
	
	
}
