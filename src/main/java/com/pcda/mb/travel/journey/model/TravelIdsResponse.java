package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class TravelIdsResponse {

	private String errorMessage;
    private int errorCode;
    private TravelIdsData response;
    private List<TravelIdsData> responseList;
}
