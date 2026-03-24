package com.pcda.mb.requestdashboard.tdr.model;

import java.util.List;

import lombok.Data;

@Data
public class GetTdrFinalProcessResponse {
	private String errorMessage;
    private int errorCode;
    private List<GetFinalProcessTdrParent> responseList;
    private GetFinalProcessTdrParent response;
}
