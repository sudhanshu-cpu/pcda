package com.pcda.mb.travel.journey.model;

import java.util.Map;

import lombok.Data;

@Data
public class FlightFareBean {

	private double adultBaseFare;
	private double adultTax;
	private double adultQ;
	private double childBaseFare;
	private double childTax;
	private double childQ;
	private double infantBaseFare;
	private double infantTax;
	private double infantQ;
	private String currency;
	private double discount;
	private String fareType;
	private double markup;
	private double plb;
	private String refundable;
	private String refundableInfo;
	private double serviceFee;
	private double serviceTax;
	private double tds;
	private double totalTax;
	private double totalQ;
	private double transactionFee;
	private double totalBaseFare;
	private double totalNet;
	private Map<String, Double> otherTaxBreakup;
	private Map<String, Double> paxOtherTaxBreakup;

}
