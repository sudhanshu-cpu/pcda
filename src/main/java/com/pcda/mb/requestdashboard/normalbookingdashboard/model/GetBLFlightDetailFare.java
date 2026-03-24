package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetBLFlightDetailFare {
	private double totalBaseFare;
	private double totalTax;
	private double totalNet;
	private double serviceFee;
	private double serviceTax;
	private double discount;
}
