package com.pcda.serviceprovider.reports.airrefundupdation.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PosrAirRefChildModel {

	
	
	private Integer	passangerNo;
	private String newCreditNoteNo;
	private String newCancellationTaxId;
	private String	passangerName;
	private Date newCreditNoteDate;
	private double	newTotalRefund;
	private double	newBaseFare;
	private double	newTax;
	private double	newPsfFee;
	private double newOtherTax;
	private double newCgstTax;
	private double newSgstTax;
	private double newIgstTax;
	private double newCanYq	;
	private double newCanAirline;
	private double newCanEducess;
	private double newCanHigherEducess;
	private double newCanRxChargeAirline;
	private double	newCanRxPenaltyCharge;
	private double	newCanServiceTax;
	private double	newEducessOnAirLine;
	private double	newHigherEducessOnAirLine;
	private double	newCanSsrAmount	;
	private double	newOtherTaxOnSegment;
	
	
}
