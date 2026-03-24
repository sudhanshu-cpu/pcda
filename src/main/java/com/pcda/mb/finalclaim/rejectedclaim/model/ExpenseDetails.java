package com.pcda.mb.finalclaim.rejectedclaim.model;

import lombok.Data;

@Data
public class ExpenseDetails {

	private double maxHotelAmount;
	private double maxFoodAmount;
	private double maxTourAmount;
	private double maxOtherAmount;
	private double maxKmRate;
	private double maxAllowedWeightKg;
	private double maxVTransportAmt;
	private double maxRationAmt;
	private int conveyanceDtls;
	private double maxLeavelAMessAmt;
	private double maxLeavelBMessAmt;
	private double maxLeavelCMessAmt;
	private int fourWheelerAllow;

}
