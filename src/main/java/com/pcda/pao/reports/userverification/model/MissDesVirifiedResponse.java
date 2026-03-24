package com.pcda.pao.reports.userverification.model;

import java.util.List;

import lombok.Data;
@Data
public class MissDesVirifiedResponse {
	
	private List<UserVerificationConfirmModel> responseList;

	private PostVerifyMissDeModel response;
	private int errorCode;

	private String errorMessage;
}
