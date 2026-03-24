package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class AirRequestResponse {

	private String errorMessage;
    private int errorCode;
    private Integer id;
    private AirRequestData response;
}
