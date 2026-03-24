package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetAttPaxInvoiceInfoModel {
	private int passangerNo;
	private int journeySequenceNo;
	private double baseFare;
	private double tax;
	private double fuelCharge;
	private double managementFee;
	private double processingFee;
	private double serviceTax;
	private double cgstTax;
	private double sgstTax;
	private double igstTax;
	private double ugstTax;
	private double k3tax;
	private double educess;
	private double totalInvoice;
	private double psfFee;
	private double otherTax;
	private double higherEducess;
	private double gstTax;

}
