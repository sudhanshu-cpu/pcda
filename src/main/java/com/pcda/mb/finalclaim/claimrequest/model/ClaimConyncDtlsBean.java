package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.Date;

import lombok.Data;

@Data
public class ClaimConyncDtlsBean {

	private String tourVehicleNo;
	private Integer tourNoOfDays;
	private Date tourDate;
	private Integer tourDistance;
	private String tourBillNo;
	private Double tourAmount;
	private Double actualTourAmount;
	private Integer conveyanceDtls;

}
