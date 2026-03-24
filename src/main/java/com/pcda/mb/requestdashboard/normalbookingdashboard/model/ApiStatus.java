package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiStatus {


    @JsonProperty("Status")
    private String status;
    @JsonProperty("StatusCode")
    private String statuscode;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Operation")
    private String operation;
    @JsonProperty("ResultFormat")
    private String resultformat;
    @JsonProperty("TransactionStatus")
    private String transactionstatus;
    @JsonProperty("Result")
    private RecordModel result;

	
}
