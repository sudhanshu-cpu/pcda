package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetFareRuleBLResponse {


	private String errorMessage;
	private int errorCode;
	private List<GetBLFareRuleResModel> responseList;
	private GetBLFareRuleResModel response;
}
