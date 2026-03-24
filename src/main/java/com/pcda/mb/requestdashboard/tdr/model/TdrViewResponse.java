package com.pcda.mb.requestdashboard.tdr.model;

import java.util.List;

import lombok.Data;

@Data
public class TdrViewResponse {

	
	private String errorMessage;
    private int errorCode;
    private List<TDRViewModel> responseList;
    private TDRViewModel response;
}
