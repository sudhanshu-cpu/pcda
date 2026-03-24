package com.pcda.mb.travel.bulkBkg.model;

import java.util.List;

import com.pcda.mb.travel.journey.model.TRRulesData;

import lombok.Data;
@Data
public class TRRulesBulkBkgResponse {
	private String errorMessage;
    private int errorCode;
    private TRRulesData response;
    private List<TRRulesDataBulkBkg> responseList;
}
