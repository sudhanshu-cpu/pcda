package com.pcda.mb.travel.journey.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PreventProcess {

	private BigInteger loginUserId;
	private String process;
	private Integer processState;
	private String groupId;
	private BigInteger personalId;
	
	
	private String actionType;
}
