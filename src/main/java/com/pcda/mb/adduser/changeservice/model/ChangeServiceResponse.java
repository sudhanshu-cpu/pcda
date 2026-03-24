package com.pcda.mb.adduser.changeservice.model;

import java.util.List;

import lombok.Data;

@Data
public class ChangeServiceResponse {

	private String errorMessage;
    private int errorCode;
    private List<GetChangeServiceModel> responseList;
    private GetChangeServiceModel response;
	
}
