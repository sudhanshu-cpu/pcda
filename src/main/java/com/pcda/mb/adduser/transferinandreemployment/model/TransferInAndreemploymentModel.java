package com.pcda.mb.adduser.transferinandreemployment.model;

import java.math.BigInteger;
import java.util.List;

import com.pcda.common.model.OfficeModel;

import lombok.Data;

@Data
public class TransferInAndreemploymentModel {

	private String serviceDtls;
	private String rankExist;
	private String categoryId;
	private String categoryName;
	private String officeString;
	private String dscMessage;
	private Integer retireAgePbor;
	private Integer retireAgeOther;
	private String visitorUnit;
	private String serviceName;
	private String serviceId;
	private String serviceType;
	private String rankExistService;
	private String serviceListWithRank;
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
	private String unitName;
	private String currentOfficeId;
	private String payAccountOffice;
	private String userServiceId;
	private String dateOfBirth;
	private String dateOfRetirement;
	private String unitServiceId;
	private String visitorUnitName;
	private List<OfficeModel> unitList;

}
