package com.pcda.mb.travel.emailticket.model;

import lombok.Data;

@Data
public class PaxCanInvoiceInfo {
	
	private int passangerNo;
	private String passangerFullName;
	private String onwardPaxId;
	private int journeySequenceNo;
	private String cancellationTaxId;
	private String creditNoteNo;
	private String creditNoteDate;
	private int invoiceStatus;
	private double calTotalRefund;
	private String cancellationDate;
	private double airlinePenalityCharge;
	private double canrxEducess;
	private double canrxHigherEducess;
	private double canrxServiceTaxOnAirline;
	private double canrxEducessOnAirline;
	private double canrxHighEducessOnAirline;
	private double canrxTax;
	private double canrxYq;
	private double canrxOtherTaxes;
	private double canrxCanxChargeAirline;
	private double canrxRefundAmount;
	private double canrxJn;
	private double fcanBaseFare;
	private double fcanCanxCharge;
	private double fcanEducess;
	private double fcanHighereducess;
	private double fcanOtherTax;
	private double fcanRefundAmount;
	private double fcanServiceTax;
	private double fcanTax;
	private double fcanTotalInvoice;
	private double fcanYq;
	private String fcanRefundedBy;
	private double cgstTax;
	private double sgstTax;
	private double igstTax;
	
}

