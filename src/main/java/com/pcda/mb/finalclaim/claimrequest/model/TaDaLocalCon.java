package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.Date;

import lombok.Data;

@Data
public class TaDaLocalCon {

	private String vehicleNo;
	private Integer noOfDay;
	private Date dateOftravel;
	private Integer distance;
	private String billNo;
	private Double billAmount;
	private Double sanctionBillAmount;
	private String deductionReason;
	private Double actualBillAmount;
	private Double oneDayMaxConAmnt;
	private Integer seqNo;
}
