package com.pcda.co.approveuser.approvechangeservice.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class GetChanSerApprovelModel {	
	private BigInteger loginUserId = BigInteger.ZERO;
	
	private String oldUnitService;
	private String oldCategory;
	private String oldUserService;
	private String oldRank;
	private String reason;
	private String oldPayAccOff;
	
	
	private String creationDate;
	private String oldLevel;
	private String oldAirPayAccOff;
	private Integer serviceEditFlag;
	private Integer seqNo;
	private String personalNumber;
	
	private String newUserService;
	private String newUnitService;
	private String newCategory;
	private String newRank;
	private String newPayAccOff;
	private String newAirPayAccOff;
	
	private String personalName;
	private String newLevel;
	
	

}
