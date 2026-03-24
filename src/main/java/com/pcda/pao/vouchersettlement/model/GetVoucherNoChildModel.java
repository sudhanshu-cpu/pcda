package com.pcda.pao.vouchersettlement.model;

import lombok.Data;

@Data
public class GetVoucherNoChildModel {
	private String txnInvoiceCreditnoteNo;
	private int seqNo;
	private String txnPaymentStatus;
	private String fbReferenceNo;	
	private Double outstandingAmount;
	private Double txnOutstandingAmount;
	private String personalNo;
	private String unit;
	private String travelerName;
	private String utrNo;
	private String paymentDate;
	private double paidAmount;
	private String paymentAckDate;

}
