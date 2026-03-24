package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class TravelIdDetailsResponse {

	private String errorMessage;
    private int errorCode;
    private TravelIdDetails response;
    private List<TravelIdDetails> responseList;
}
