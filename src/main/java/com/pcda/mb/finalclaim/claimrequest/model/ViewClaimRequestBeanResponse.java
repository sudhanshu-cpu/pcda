package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.List;

import lombok.Data;

@Data
public class ViewClaimRequestBeanResponse {

	private String errorMessage;

	private Integer errorCode;

	private ViewClaimRequestBean response;

	private List<ViewClaimRequestBean> responseList;
}
