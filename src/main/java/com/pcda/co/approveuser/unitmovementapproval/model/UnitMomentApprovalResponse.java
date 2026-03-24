package com.pcda.co.approveuser.unitmovementapproval.model;

import java.util.List;

import lombok.Data;

@Data
public class UnitMomentApprovalResponse {

	
	private String errorMessage;

	private Integer errorCode;

	private GetUnitMomentApprovalModel response;

	private List<GetUnitMomentApprovalModel> responseList;
	
	
}
