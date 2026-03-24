package com.pcda.cgda.airtranscation.voucherdownload.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class VoucherPostModel {

	private String fromDate;
	private String toDate;
	private String serviceId;
	private String travelType;
	private String paoId;
	private Integer  serviceProvider;
	private BigInteger loginUserId;
}
