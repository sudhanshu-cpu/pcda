package com.pcda.co.approveuser.approvetraveller.model;

import lombok.Data;

@Data
public class TravellerApprovalResponse {

	private String errorMessage;

	private int errorCode;

	private TravellerApprovalModel response;

}
