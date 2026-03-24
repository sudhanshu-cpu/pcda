package com.pcda.pao.RailDemand.RailIrla.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class IRLAAdjustmentModel {

	private BigInteger loginUserId;
	private String dwnRequestId;
	private String adjustmentDate;
}
