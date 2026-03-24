package com.pcda.mb.travel.journey.model;

import lombok.Data;

import java.util.List;

@Data
public class ViaRouteStationsModel {
    private String errorMessage;
    private int errorCode;
    private String request;
    private String customMessage;
    private String response;
    private List<String> responseList;

}
