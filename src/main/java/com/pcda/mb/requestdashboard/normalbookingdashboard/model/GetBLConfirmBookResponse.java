package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class GetBLConfirmBookResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetBLBookInfoModel> responseList;
	private GetBLBookInfoModel response;
}
