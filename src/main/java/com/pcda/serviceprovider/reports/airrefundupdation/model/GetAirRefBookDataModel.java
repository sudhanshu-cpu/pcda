package com.pcda.serviceprovider.reports.airrefundupdation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAirRefBookDataModel {
	
	private String bookingId;
	private String requestId;
	
	private String serviceProvider;
	private int serviceProviderInt;
	

}
