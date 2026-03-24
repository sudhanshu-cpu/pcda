package com.pcda.adg.reports.passengeraddressreport.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data

public class GetPassengerAddresModel {
	private String pnrNo;
	private String trainNo;

	private String trainName;
	private String journeyDateFormat;
	private Date journeyDate;

	private String frmStn;

	private String toStn;
	private String unitName;
	private String destinationAddress;
	private BigInteger userId;
	private String passengerName;
	private String showData;

}
