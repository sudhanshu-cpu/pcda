package com.pcda.mb.travel.bulkBkg.model;

import java.util.List;

import com.pcda.mb.travel.journey.model.PreventProcessDetails;

import lombok.Data;
@Data
public class PreventProcessBulkBkgResponse {
	
	private String errorMessage;
    private int errorCode;
    private Integer id;
    private PreventProcessDetails response;
    private List<PreventProcessDetailsBulkBkg> responseList;

}
