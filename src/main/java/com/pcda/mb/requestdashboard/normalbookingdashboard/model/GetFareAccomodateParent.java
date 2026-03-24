package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GetFareAccomodateParent {
	private String errorMessage;
	private int nChild ;
	private int nAdult;
	private int nSenior;
	private int nInfant;
	private int nWSenior;
	private GetAccmodationBean accmodationBean;
	private List<GetRailFareBean> fareBean=new ArrayList<>();
	
	private double adultBaseFare;
	private double childBaseFare;
	private double seniorBaseFare;
	private double wseniorBaseFare;
	private double reservationCharge;
	private double otherCharges;
	private double suparfastCharges;
	private double concAmount;
	private double totalPassengerServiceTax;
	private double totalPassengerCateringCharge;
	private double totalPassengerConcession;
	private double totalPassengerCollectibleAmount;
	private double totalPassengerFuelAmount;
	private double totalPassengerTatkalFare;
	private double totalPassengerWpServiceCharge;
	private double totalPassengerWpServiceTax;
	private double totalPassengerSaftyCharge;
	private double totalIrctcFee;
	private double totalPassengerFare;
	private String bookingConfig;
	private long distance;

}
