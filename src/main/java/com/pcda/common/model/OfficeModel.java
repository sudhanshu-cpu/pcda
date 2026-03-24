package com.pcda.common.model;


import com.pcda.util.Status;

import lombok.Data;

@Data
public class OfficeModel {

	private Integer oId;
	private String name;
	private Status status;
	private String approvalState;
	private String groupId;
	private String serviceId;
	private String serviceName;
	private String groupDescription;
	private String email;
	private String addressOne;
	private String addressTwo;
	private String armyPhone;
	private String civilPhone;
	private String armyFax;
	private String civilFax;
	private String ipAddress;
	private Integer serviceType;
	private String serviceTypeName;
	private String recordOfficeNRS;
	private String recordOfficeNAP;
	private String recordOfficeName;
	private String cgdaAssociatedCode;
	private String adgmovAssociatedCode;
	private String laoAssociatedCode;
	private String accOfficeCode;
	private String paoGroupId;
	private String locationTypeId;
	private String intTravelAllow;
	private String paoAirGroupId;
	private Integer hospitalUnit;

}
