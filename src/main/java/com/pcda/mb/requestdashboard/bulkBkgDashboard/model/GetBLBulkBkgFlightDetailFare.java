package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import lombok.Data;

@Data
public class GetBLBulkBkgFlightDetailFare {
	
	private double totalBaseFare;
	private double totalTax;
	private double totalNet;
	private double serviceFee;
	private double serviceTax;
	private double discount;

}
