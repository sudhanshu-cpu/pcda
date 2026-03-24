package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.List;

import lombok.Data;

@Data
public class GetCityResponse {

	private List<GetCityBean> responseList;
	private Integer errorCode;
	private String errorMessage;

}
