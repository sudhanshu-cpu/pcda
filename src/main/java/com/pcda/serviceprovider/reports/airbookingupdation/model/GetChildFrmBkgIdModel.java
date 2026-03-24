package com.pcda.serviceprovider.reports.airbookingupdation.model;

import lombok.Data;

@Data
public class GetChildFrmBkgIdModel {

	
	
	private int passangerNo;
	private String passangerName;
	private String invoiceNo;
	private String onwardPaxId;
	private String invoiceDate;
	
	private Double totalInvoice;
	private Double baseFare;
	private Double tax;
	private Double fuelCharge;
	private Double psfFee;
	private Double otherTax;
	private Double cgstTax;
	private Double sgstTax;
	private Double igstTax;
	private Double gst;

}
