package com.pcda.mb.adduser.disapprovedprofile.model;

import java.util.List;

import lombok.Data;

@Data
public class DisapprovedProfileResponse {

	
	private String errorMessage;

	private Integer errorCode;

	private DisProfileReqModel response;

	private List<DisProfileReqModel> responseList;

	
}
