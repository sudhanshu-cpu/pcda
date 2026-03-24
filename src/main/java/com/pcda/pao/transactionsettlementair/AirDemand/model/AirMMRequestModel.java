package com.pcda.pao.transactionsettlementair.AirDemand.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class AirMMRequestModel {

	private BigInteger loginUserId;
	private String dwnRequestId;
	List<AirDemandMasterMissingAdjustmentModel> airRequestDataList;
}
