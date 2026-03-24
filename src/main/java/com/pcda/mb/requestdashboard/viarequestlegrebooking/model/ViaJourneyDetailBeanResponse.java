package com.pcda.mb.requestdashboard.viarequestlegrebooking.model;

import java.util.List;

import lombok.Data;

@Data
public class ViaJourneyDetailBeanResponse {

	
	
	private String errorMessage;
	private int errorCode;
	private List<ViaJourneyDetailBean> responseList;
	private ViaJourneyDetailBean response;
}
