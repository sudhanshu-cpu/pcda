package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class CounterPersonalInfoResponse {

	private Integer errorCode;
	private CounterPersonalInfoBean response;
	private String errorMessage;

}
