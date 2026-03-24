package com.pcda.co.approveuser.approvebulktransferin.model;

import java.util.List;

import lombok.Data;

@Data
public class BulkTransferInApprovalResponse {
	
	private String errorMessage;
	private Integer errorCode;
	private BulkTransferApprovalModel response;
	private List<BulkTransferApprovalModel> responseList;

}
