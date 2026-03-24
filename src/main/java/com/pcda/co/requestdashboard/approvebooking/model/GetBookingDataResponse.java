package com.pcda.co.requestdashboard.approvebooking.model;

import java.util.List;

import lombok.Data;

@Data
public class GetBookingDataResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetApproveBookingBean> responseList;
	private GetApproveBookingBean response;
}
