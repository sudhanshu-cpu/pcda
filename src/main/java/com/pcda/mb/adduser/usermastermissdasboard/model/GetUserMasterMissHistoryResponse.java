package com.pcda.mb.adduser.usermastermissdasboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetUserMasterMissHistoryResponse {
	private String errorMessage;

	private int errorCode;

	private GetUserMasterMissHistoryModel response;

	private List<GetUserMasterMissHistoryModel> responseList;

}
