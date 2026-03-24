package com.pcda.serviceprovider.reports.airrefundupdation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAirRefBookChildModel {

	private int passangerNo;
	private String passangerName;
	private String cancellationTaxId;
	private String creditNoteNo;
	private String creditNoteDate;

	private Double totalRefund;
	private Double canBaseFare;
	private Double canYq;
	private Double cnaPsfFee;
	private Double canOtherTax;
	private Double canAirline;

	private Double canEducess;
	private Double canHigherEducess;

	private Double cgstTax;
	private Double igstTax;
	private Double sgstTax;
	private Double canTax;

	private Double canRxChargeAirline;
	private Double canRxPenaltyCharge;
	private Double canServiceTax;
	private Double canEducessOnAirLine;
	private Double canHigherEducessOnAirLine;
	private Double canSsrAmount;
	private Double canOtherTaxOnSegment;

}
