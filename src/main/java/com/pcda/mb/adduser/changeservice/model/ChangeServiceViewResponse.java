package com.pcda.mb.adduser.changeservice.model;

import java.util.List;

import lombok.Data;

@Data
public class ChangeServiceViewResponse {


	private String errorMessage;
    private int errorCode;
    private List<GetChangeServiceViewModel> responseList;
    private GetChangeServiceViewModel response;
	
}
