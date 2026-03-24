package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class JrnyFairRuleResponse {

	private String errorMessage;
	private int errorCode;
	private List<JrnyFareRuleResponse> responseList;
	private JrnyFareRuleResponse response;

}
