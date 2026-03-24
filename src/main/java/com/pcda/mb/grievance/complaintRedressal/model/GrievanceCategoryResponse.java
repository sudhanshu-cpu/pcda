package com.pcda.mb.grievance.complaintRedressal.model;

import java.util.List;

import com.pcda.common.model.GrievanceCategoryBean;
import com.pcda.mb.finalclaim.claimrequest.model.FinalClaimReqSearchModel;

import lombok.Data;

@Data
public class GrievanceCategoryResponse {
	
	private List<GrievanceCategoryBean> responseList;

	private int errorCode;

	private String errorMessage;
	
}
