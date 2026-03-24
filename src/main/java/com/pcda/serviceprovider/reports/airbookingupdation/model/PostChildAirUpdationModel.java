package com.pcda.serviceprovider.reports.airbookingupdation.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostChildAirUpdationModel {

	
	
	private Integer passangerNo;
	private String passangerName;
	private String newInvoiceNo;
	private String newOnwardPaxId;
	private Date newInvoiceDate;
	
	private double newTotalInvoice;
	private double newBaseFare;
	private double newTax;
	private double newFuelCharge;
	private double newPsfFee;
	private double newOtherTax;
	private double newCgstTax;
	private double newSgstTax;
	private double newIgstTax;
	private double newGst;

}
