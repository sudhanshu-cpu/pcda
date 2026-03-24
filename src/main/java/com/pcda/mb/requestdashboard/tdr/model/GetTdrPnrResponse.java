package com.pcda.mb.requestdashboard.tdr.model;

import java.util.List;

import lombok.Data;

@Data
public class GetTdrPnrResponse {

	

	private String errorMessage;
    private int errorCode;
    private List<GetParentTdrModel> responseList;
    private GetParentTdrModel response;
}
