package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class TaggedRequestResponse {

	private String errorMessage;
    private int errorCode;
    private Integer id;
    private TaggedRequestModel response;
}
