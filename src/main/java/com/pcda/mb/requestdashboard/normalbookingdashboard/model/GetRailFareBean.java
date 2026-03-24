package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetRailFareBean {
	private String passengerType;
	private double baseFare;
	private double reservCharge;
	private double superfastCharge;
	private double otherCharge;
	private double total;
	private double serviceTax;
	private double cateringCharge;
	private double concAmount;
	private double netAmount;
	private double fuelAmount;
	private double tatkalFare;
	private double wpServiceCharge;
	private double wpServiceTax;
	private double saftyCharge;
	private double dynamicfare;

}
