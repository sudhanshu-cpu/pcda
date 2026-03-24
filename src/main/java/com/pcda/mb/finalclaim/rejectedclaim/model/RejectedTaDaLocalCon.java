package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.Date;

import lombok.Data;

@Data
public class RejectedTaDaLocalCon {

	private String vehicleNo;
	private Integer noOfDay;
	private Date dateOftravel;
	private String dateOftravelStr;
	private Integer distance;
	private String billNo;
	private Double billAmount;
	private Double sanctionBillAmount;
	private String deductionReason;
	private Double actualBillAmount;
	private Double oneDayMaxConAmnt;
	private Integer seqNo;

}
