package com.pcda.mb.travel.emailticket.model;

import lombok.Data;

@Data
public class PaxInvoiceInfo {
	
	
	private float baseFare;
	private Double tax;
	
	private float fuleCharge;
	private Double managementFee;
	
	private Double processingFee;
	private Double serviceTax;
	
	private Double educess;
	private float totalInvoice;
	
	private float cgstTax;
	private Double sgstTax;

	private Double igstTax;
	private Double ugstTax;
	
	private Double k3Tax;
	private float gstTax;

	private float otherTax;
	
	private Integer passangerNo;
	private Integer journeySequenceNo;
	
	private Integer psfFee;
	
	private Integer higherEducess;

}

