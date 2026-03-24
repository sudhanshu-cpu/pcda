package com.pcda.mb.travel.journey.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class JrnyRecordModel {

	@JsonProperty("Rule")
    private String rule;

}
