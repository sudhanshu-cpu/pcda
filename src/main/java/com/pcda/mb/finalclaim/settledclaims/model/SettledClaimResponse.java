package com.pcda.mb.finalclaim.settledclaims.model;

import java.util.List;

import lombok.Data;

@Data
public class SettledClaimResponse {

	private String errorMessage;

	private Integer errorCode;

	private SettledClaimModel response;

	private List<SettledClaimModel> responseList;
}
