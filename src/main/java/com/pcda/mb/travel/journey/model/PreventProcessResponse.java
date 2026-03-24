package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class PreventProcessResponse {

	private String errorMessage;
    private int errorCode;
    private Integer id;
    private PreventProcessDetails response;
    private List<PreventProcessDetails> responseList;
}
