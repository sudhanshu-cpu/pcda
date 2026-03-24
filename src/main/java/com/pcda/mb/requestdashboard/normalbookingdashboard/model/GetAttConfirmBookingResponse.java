package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAttConfirmBookingResponse {
	private String errorMessage;
	private int errorCode;
	private List<GetAttBookInfoModel> responseList;
	private GetAttBookInfoModel response;
}
