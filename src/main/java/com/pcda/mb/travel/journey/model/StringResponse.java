package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class StringResponse {

	private String errorMessage;
    private int errorCode;
    private String response;
    private List<String> responseList;
}
