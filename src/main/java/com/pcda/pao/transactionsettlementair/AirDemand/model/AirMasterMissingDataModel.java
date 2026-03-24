package com.pcda.pao.transactionsettlementair.AirDemand.model;

import java.math.BigInteger;

import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class AirMasterMissingDataModel {

    private String dwnRequestId;
	
	private BigInteger userId;

	private String personalNo;
	private String name;

	private String cdaoAccountNo;
	private Integer irlaCount;

	private YesOrNo masterMiss = YesOrNo.NO;

	private String comments;
}
