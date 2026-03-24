package com.pcda.serviceprovider.reports.airbookingupdation.model;

import lombok.Data;

@Data
public class GetSecChildDataFrmBkgModel {

	private String bookingId;
	private String requestId;
	private double totalInvoice;
	private String operatorTxnId;
	private String serviceProvider;
	private int serviceProviderInt;
	

	
}
