package com.pcda.mb.travel.journey.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class JrnyFareRuleResponse {

	 @JsonProperty("ApiStatus")
	private JrnyApiStatus  apiStatus;
}
