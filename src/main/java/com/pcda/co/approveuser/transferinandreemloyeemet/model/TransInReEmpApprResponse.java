package com.pcda.co.approveuser.transferinandreemloyeemet.model;

import java.util.List;

import lombok.Data;

@Data
public class TransInReEmpApprResponse {


	private String errorMessage;

	private Integer errorCode;

	private TransferInReemployeApprovalModel response;

	private List<TransferInReemployeApprovalModel> responseList;
	
}
