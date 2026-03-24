package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class FinancialYearResponse {

	private String errorMessage;
    private int errorCode;
    private List<FinancialYearModel> responseList;
    private FinancialYearModel response;
}
