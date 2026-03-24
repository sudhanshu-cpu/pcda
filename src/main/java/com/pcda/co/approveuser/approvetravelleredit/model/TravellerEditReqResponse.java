package com.pcda.co.approveuser.approvetravelleredit.model;

import java.util.List;

import lombok.Data;

@Data
public class TravellerEditReqResponse {

	private List<TravellerEditReq> responseList;

	private int errorCode;

	private String errorMessage;

}
