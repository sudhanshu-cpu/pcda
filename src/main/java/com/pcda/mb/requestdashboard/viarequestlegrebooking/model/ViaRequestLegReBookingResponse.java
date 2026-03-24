package com.pcda.mb.requestdashboard.viarequestlegrebooking.model;

import java.util.List;

import lombok.Data;

@Data
public class ViaRequestLegReBookingResponse {
	
	private String errorMessage;
    private int errorCode;
    private List<ViaRequestLegReBookingModel> responseList;
    private ViaRequestLegReBookingModel response;

}
