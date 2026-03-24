package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetAttInvoiceInfoModel {
	private double baseFare;
	private double tax;
	private double fuleCharge;
	private double managementFee;
	private double processingFee;
	private double serviceTax;
	private double educess;
	private double totalInvoice;
	private double cgstTax;
	private double sgstTax;
	private double igstTax;
	private double ugstTax;
	private double k3Tax;
	private double gstTax;
	private double otherTax;
}
