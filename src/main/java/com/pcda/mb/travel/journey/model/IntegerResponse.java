package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class IntegerResponse {

	private String errorMessage;
    private int errorCode;
    private Integer id;
    private Integer response;
    private List<Integer> responseList;
    
}
