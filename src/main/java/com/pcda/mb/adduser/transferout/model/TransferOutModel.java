package com.pcda.mb.adduser.transferout.model;

import java.math.BigInteger;
import java.util.List;

import com.pcda.common.model.OfficeModel;

import lombok.Data;

@Data
public class TransferOutModel {

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
	private String payAccountOffice;
	private String userServiceId;
	private String currentOfficeId;
	private List<OfficeModel> unitList;

}
