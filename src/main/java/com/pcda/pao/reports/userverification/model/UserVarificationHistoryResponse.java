package com.pcda.pao.reports.userverification.model;

import java.util.List;

import lombok.Data;
@Data 
public class UserVarificationHistoryResponse {

	private String errorMessage;

	private int errorCode;

	private UserVerificationHistoryModel response;

	private List<UserVerificationHistoryModel> responseList;
}
