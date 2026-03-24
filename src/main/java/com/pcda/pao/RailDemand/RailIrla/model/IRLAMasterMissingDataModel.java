package com.pcda.pao.RailDemand.RailIrla.model;

import java.math.BigInteger;

import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class IRLAMasterMissingDataModel {

	private String dwnRequestId;
	private BigInteger userId;
	private String personalNo;
	private Integer irlaCount;
	private Integer drCount;
	private Integer crCount;
	private YesOrNo masterMiss=YesOrNo.NO;
	private String comments;
}
