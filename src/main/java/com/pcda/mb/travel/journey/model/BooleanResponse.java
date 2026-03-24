package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class BooleanResponse {

	private String errorMessage;
    private int errorCode;
    private Integer id;
    private Boolean response;
    private List<Boolean> responseList;
    
}
