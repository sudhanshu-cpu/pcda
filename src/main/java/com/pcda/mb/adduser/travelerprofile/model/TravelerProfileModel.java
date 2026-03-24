package com.pcda.mb.adduser.travelerprofile.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class TravelerProfileModel {

	private String personalNoBlock;

	private String ersPrintName;

	private String accountOffice;
	private String airAccountOffice;

	private String nrsDutyStation;
	private String homeTown;
	private String nrsHomeTown;
	private String sprSFA;
	private String sprNRS;
	private String dutyStationNA;
	private String homeTownNA;
	private String sprNA;

	private int spouseGovEmployee = 1;

	private String spousePanNo;

	private int onSuspension = 1;
	private int abandoned = 1;

	private String ltcCurrentYear;
	private String ltcPreviousYear;
	private String ltcCurrentSubBlock;
	private String ltcPreviousSubBlock;

	private String accountNumber;

	private Date commisioningDate;

	private Integer cvFormDUsed;

	private BigInteger modifiedBy;
	private String serviceNo;

}
