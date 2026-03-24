package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class DAAdvanceDTO {

	private String noOfDays;
	private String returnDate;
	private String destinationCity;
	private String destinationCityGrade;
	private String govtAcc;
	private String advanceAmt;
	private String office;
	private String basicPay;
	private String luggageAmt;
	private String conveyance;
	private String newDaOrOldDa;
	private String requestForHotelDA;
	private String requestForConveyanceDA;
	private String requestForFoodDA;
	private String hotelAmount;
	private String conveyanceAmount;
	private String foodAmount;
	private String totalCtg;
	private int ptTransferType;

}
