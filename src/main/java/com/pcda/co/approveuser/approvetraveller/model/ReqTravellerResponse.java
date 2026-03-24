package com.pcda.co.approveuser.approvetraveller.model;

import java.util.List;

import lombok.Data;

@Data
public class ReqTravellerResponse {

	private List<ReqTravellerSearch> responseList;
	private TravellerApprovalReq response;
	private int errorCode;
	private String errorMessage;

}
