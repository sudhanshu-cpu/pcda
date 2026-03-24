package com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model;


import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostOutVouchGenCgdaChildModel {



	private int verifyStatus;
	private int bkgVoucherStatus;
	private String reasonText;
	private String procValidForVoucher;
	private String validForVoucher;
	private String voucherTxnType;
	private String travelType;
	private String firstName;
	private String lastName;
	private String personalNo;
	private String cdaoAcNo;
	private String unit;
	private String paoName;
	private String authorityNo;
	private String authorityDate;
	private String dgcaApporvalNo;
	private String dgcaApporvalDate;
	private String fbNo;
	private String uniqueTransactionId;
	private String transactionId;
	private String transactionDate;
	private Double baseFare;
	private Double yq;
	private Double otherTax;
	private Double ssrAmount;
	private Double serviceTax;
	private Double educess;
	private Double higherEducess;
	private Double cancellationCharges;
	private Double transactionAmount;
	private String transactionNature;
	private String status;
	private String cancellationGround;
	private String ltcFare;
	private String referenceId;
	private String referenceIdDate;
	private String bookingId;
	private String requestId;
	private String amountDrCr;
	private String referenceIdAmount;
	private String referenceIdNature;
	private Double netOutstanding;
	private String natureNetOutstanding;
	private Double dueAmount;
	private String dueAmountNature;
	private String travelDate;
	private String travelerTitle;
	private String travelerFirstName;
	private String travelerLastName;
	private String travelerGender;
	private int travelerAge;
	private String travelerRelation;
	private String ticketNo;
	private String gdsPnrNo;
	private String airlinePnrNo;
	private String airlineCode;
	private String sectorDetail;
	private String flightNo;
	private String bookingClass;
	private String journeyType;
	private Double cgstTax;
	private Double sgstTax;
	private Double igstTax;
	private Double jn;
	private Double ugstTax;
	private Double k3Tax;
	private Double gstTax;
	private int airCompDisplayType;
	private BigInteger userId;
	private int transactionStatus;
	private Double refAmount;
	private int travelerTitleInt;
	
	
	

    

	
	
}
