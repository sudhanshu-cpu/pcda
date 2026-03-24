package com.pcda.mb.adduser.transferinandreemployment.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class PostTransferInReemployement {

	private BigInteger loginUserId;
	private String categoryId;
	private String categoryName;
	private Integer retireAgePbor;
	private Integer retireAgeOther;
	private String serviceName;
	private String serviceId;
	private BigInteger userId;
	private String dutyStnNa;
	private String isUnitInPeaceLoc;
	private String sprPlace;
	private String sprNrs;
	private String sprNa;
	private String reason;
	private String sosDateString;
	private Date sosDate;
	private String unitName;
	private String currentOfficeId;
	private String payAccountOffice;
	private String dateOfRetirement;
	private String profileChangeAtrribute;
	private String unitNo;
	private String currentUnit;
	private Date joiningDate;
	private String seqNo;

	private String altServiceId;
	private String unitServiceId;

	private String paoGroupIdByUnitId;
	private String airPaoGroupIdByUnitId;

	private String rankId;
	private String category;

	private String railPayAcOff;
	private String airPayAcOff;

	private String levelName;
	private String transferTo;
	private String levelId;
	private String level_Id;
	private String unit;
	private String nrsDutyStn;

	private String sprSfa;

	private String accountOfice;

}
