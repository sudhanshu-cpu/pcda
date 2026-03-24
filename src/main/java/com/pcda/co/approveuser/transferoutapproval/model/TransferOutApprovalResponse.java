package com.pcda.co.approveuser.transferoutapproval.model;

import java.util.List;

import lombok.Data;

@Data
public class TransferOutApprovalResponse {

	
	private String errorMessage;

	private Integer errorCode;

	private TransferOutApprovalModel response;

	private List<TransferOutApprovalModel> responseList;
}
