package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class TravelRuleResponse {

	private String errorMessage;

	private int errorCode;

	private List<TravelRule> responseList;

	private TravelRule response;
}
