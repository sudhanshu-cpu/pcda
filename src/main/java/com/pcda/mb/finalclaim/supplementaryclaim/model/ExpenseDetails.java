package com.pcda.mb.finalclaim.supplementaryclaim.model;

import lombok.Data;

@Data
public class ExpenseDetails {

	private Double maxHotelAmount;
	private Double maxFoodAmount;
	private Double maxTourAmount;
	private Double maxOtherAmount;
	private Double maxKmRate;
	private Double maxAllowedWeightKg;
	private Double maxVTransportAmt;
	private Double maxRationAmt;
	private Integer conveyanceDtls;
	private Double maxLeavelAMessAmt;
	private Double maxLeavelBMessAmt;
	private Double maxLeavelCMessAmt;
	private Integer fourWheelerAllow;

}
