package com.pcda.pao.RailDemand.RailIrla.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class IrlaMMRequestModel {

	private BigInteger loginUserId;
	private String dwnRequestId;
	List<IrlaMasterMissingAdjustmentModel> irlaRequestDataList;
}
