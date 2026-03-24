package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class CitySearchResponse {

	private String errorMessage;
    private int errorCode;
    private List<CitySearchModel> responseList;
    
}
