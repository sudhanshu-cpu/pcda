package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import lombok.Data;

@Data
public class GetATTBulkBkgFlightDetailFare {
	
	private double totalBaseFare;
	private double totalTax;
	private double totalNet;
	private double serviceFee;
	private double serviceTax;
	private double discount;

}
