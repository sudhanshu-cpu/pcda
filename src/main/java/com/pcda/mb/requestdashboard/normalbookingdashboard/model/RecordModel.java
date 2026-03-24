package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RecordModel {



    @JsonProperty("Rule")
    private String rule;

}
