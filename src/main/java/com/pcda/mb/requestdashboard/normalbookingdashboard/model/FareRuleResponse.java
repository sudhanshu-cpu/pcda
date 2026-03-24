package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FareRuleResponse {
	 @JsonProperty("ApiStatus")
	private ApiStatus  apiStatus;
}
