package com.pcda.mb.reports.railtravelreports.model;

import lombok.Data;

@Data
public class BookingCancellationDetailsResponse {
	
	private String errorMessage;
    private int errorCode;
    private GetRailCancelletionDetailsModel response;

	
	

}
