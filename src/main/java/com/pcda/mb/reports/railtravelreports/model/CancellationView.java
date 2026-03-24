package com.pcda.mb.reports.railtravelreports.model;

import java.util.List;

import lombok.Data;

@Data
public class CancellationView {
	
	private Integer refundId;
	private String refundDate;	
	private double refundamount;
	private double cancellationAmount;
	private String isOnGovInt;
	private String reconStatus;
	private String cancellationDate;	
	private String transactionDate;
	private String cancellationMode;
	
	private List<PassengerTicketsCancellationModel> passengerDetails;
	
	
	

  

	
	

}
