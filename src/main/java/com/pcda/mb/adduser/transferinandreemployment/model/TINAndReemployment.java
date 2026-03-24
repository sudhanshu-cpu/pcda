package com.pcda.mb.adduser.transferinandreemployment.model;

import java.math.BigInteger;
import java.util.List;

import com.pcda.common.model.OfficeModel;

import lombok.Data;

@Data
public class TINAndReemployment {

	private String message="Null";
	private String serviceName;
	private String unitServiceName;
	private String unitName;
	private String categoryName;
	private String officeId;
	private String visitorUnitName;
	private String name;
	private String dob;
	private String currentUnit;
	private BigInteger userId;
	private String personalNo;
	private List<OfficeModel> unitList;
}
