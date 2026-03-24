package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class JrnyFareRuleBLResponse {
	private String errorMessage;
	private int errorCode;
	private List<JrnyBLFareRuleResModel> responseList;
	private JrnyBLFareRuleResModel response;

}
