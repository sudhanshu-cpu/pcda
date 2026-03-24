package com.pcda.co.requestdashboard.claimreqapproval.model;

import java.util.List;

import lombok.Data;

@Data
public class ApprovalClaimRequestBeanResponse {

	private String errorMessage;

	private Integer errorCode;

	private ApprovalClaimRequestBean response;

	private List<ApprovalClaimRequestBean> responseList;
}
