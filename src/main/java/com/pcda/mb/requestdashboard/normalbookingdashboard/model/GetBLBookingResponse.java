package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetBLBookingResponse {
	private String errorMessage;
	private int errorCode;
	private List<GetBLPassBokkModel> responseList;
	private GetBLPassBokkModel response;
}
