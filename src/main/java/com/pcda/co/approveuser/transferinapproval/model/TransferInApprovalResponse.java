package com.pcda.co.approveuser.transferinapproval.model;

import java.util.List;

import lombok.Data;

@Data
public class TransferInApprovalResponse {

	
	private String errorMessage;

	private Integer errorCode;

	private TransferInApprovalModel response;

	private List<TransferInApprovalModel> responseList;
	
	
}
