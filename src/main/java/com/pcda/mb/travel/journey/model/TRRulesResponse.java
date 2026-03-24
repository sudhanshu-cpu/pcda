package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class TRRulesResponse {

	private String errorMessage;
    private int errorCode;
    private TRRulesData response;
    private List<TRRulesData> responseList;
}
