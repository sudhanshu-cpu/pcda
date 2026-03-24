package com.pcda.mb.travel.journey.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class JrnyApiStatus {

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
    private JrnyRecordModel result;

}
