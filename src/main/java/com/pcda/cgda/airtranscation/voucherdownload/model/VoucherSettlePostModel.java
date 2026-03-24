package com.pcda.cgda.airtranscation.voucherdownload.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class VoucherSettlePostModel {

	private BigInteger loginUserId;
	private String dwnRequestId;
	private String utrDate;
	private String utrNumber;
}
