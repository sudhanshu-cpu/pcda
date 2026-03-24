package com.pcda.mb.reports.railtravelreports.model;

import java.util.List;

import lombok.Data;

@Data
public class BookingDetailsResponseModel {
	
	private String errorMessage;
    private int errorCode;
    
    
    private List<GetBookingDetailsModel> responseList;
    
    private GetBookingDetailsModel response;

	
	

}
