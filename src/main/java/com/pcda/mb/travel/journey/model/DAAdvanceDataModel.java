package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class DAAdvanceDataModel {

	private double maxHotelAmt;
	private double maxConveyanceAmt;
	private double maxFoodAmt;
	private double maxMiscAmt;
	private int hotelAllowed;
	private int conveyanceAllowed;
	private int foodAllowed;
	private int miscAllowed;
	private double totalAmount;
	private double maxKilometerRate;
	private long maxAllowedWeight;
	
}
