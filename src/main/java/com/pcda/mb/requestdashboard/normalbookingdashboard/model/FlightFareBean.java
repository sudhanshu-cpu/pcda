package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class FlightFareBean implements Serializable{

	private static final long serialVersionUID = -1919648787110393211L;
	
	private Double adultBaseFare;
	private Double adultTax;
	private Double adultQ;
	private Double childBaseFare;
	private Double childTax;
	private Double childQ;
	private Double infantBaseFare;
	private Double infantTax;
	private Double infantQ;
	private String currency;
	private Double discount;
	private String fareType;
	private Double markup;
	private Double plb;
	private String refundable;
	private String refundableInfo;
	private Double serviceFee;
	private Double serviceTax;
	private Double tds;
	private Double totalTax;
	private Double totalQ;
	private Double transactionFee;
	private Double totalBaseFare;
	private Double totalNet;
	private Map<String, Double> otherTaxBreakup;
	private Map<String, Double> paxOtherTaxBreakup;

}
