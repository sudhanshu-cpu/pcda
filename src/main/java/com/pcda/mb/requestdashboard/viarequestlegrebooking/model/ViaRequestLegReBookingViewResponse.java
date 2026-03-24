package com.pcda.mb.requestdashboard.viarequestlegrebooking.model;

import java.util.List;

import lombok.Data;

@Data
public class ViaRequestLegReBookingViewResponse {

	private String errorMessage;
    private int errorCode;
    private List<ViaRequestLegReBookingViewModel> responseList;
    private ViaRequestLegReBookingViewModel response;
	
}
