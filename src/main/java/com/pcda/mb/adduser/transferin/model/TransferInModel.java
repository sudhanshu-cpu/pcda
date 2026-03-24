package com.pcda.mb.adduser.transferin.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class TransferInModel {

	
	private String actionType;

	private String personalNumber;

	private String name;

	private String userAlias;

	private BigInteger userId;

	private String travelerService;
	private String dutyStnNrs;
	private String dutyStnNa;
	private String isUnitInPeaceLoc;
	private String sprPlace;
	private String sprNrs;
	private String sprNa;
	private String reason;

	private String transferToUnits;
	private String sosDate;
	private String serviceId;
	private String serviceName;
	private String unitName;
	
	private String currentOfficeId;
private String	payAccountOffice;
	private String userServiceId;
	
}
