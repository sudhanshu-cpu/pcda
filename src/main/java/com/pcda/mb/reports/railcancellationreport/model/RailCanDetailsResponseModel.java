package com.pcda.mb.reports.railcancellationreport.model;


import lombok.Data;


@Data
public class RailCanDetailsResponseModel {

    private String errorMessage;
    private int errorCode;
    private RailCanDetailsModel response;



}
