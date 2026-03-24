package com.pcda.mb.adduser.usermastermissdasboard.model;

import java.util.List;

import lombok.Data;
@Data
public class UserMasterMissProfileResponse {
	
	private String errorMessage;

	private int errorCode;

	private GetUserMasterMissProfileModel response;

	private List<GetUserMasterMissProfileModel> responseList;


}
