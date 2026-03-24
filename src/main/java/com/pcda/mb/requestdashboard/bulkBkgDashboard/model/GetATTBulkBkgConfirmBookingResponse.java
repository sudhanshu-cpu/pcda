package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetATTBulkBkgConfirmBookingResponse {
	
	private String errorMessage;
	private int errorCode;
	private List<GetATTBulkBkgInfoModel> responseList;
	private GetATTBulkBkgInfoModel response;
	

}
