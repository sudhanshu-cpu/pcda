package com.pcda.mb.adduser.disapprovedprofile.model;

import java.util.List;

import lombok.Data;
@Data
public class DisapResponse {
	

	private String errorMessage;

	private Integer errorCode;

	private DisapprovedProfileModel response;

	private List<DisapprovedProfileModel> responseList;

}
