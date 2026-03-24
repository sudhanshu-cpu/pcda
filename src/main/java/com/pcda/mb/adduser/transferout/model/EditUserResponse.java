package com.pcda.mb.adduser.transferout.model;

import java.util.List;

import lombok.Data;
@Data
public class EditUserResponse {
	private String errorMessage;

	private int errorCode;

	private EditUserModel response;

	private List<EditUserModel> responseList;

}
