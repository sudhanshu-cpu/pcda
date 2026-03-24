package com.pcda.co.approveuser.approvechangepersonalno.model;

import java.util.List;

import lombok.Data;

@Data
public class ApprovechangePnoResponse {

	private String errorMessage;
    private int errorCode;
    private List<GetApproveChangePnoModel> responseList;
    private GetApproveChangePnoModel response;
}
