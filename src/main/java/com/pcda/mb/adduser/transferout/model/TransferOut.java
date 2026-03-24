package com.pcda.mb.adduser.transferout.model;

import java.math.BigInteger;
import java.util.List;

import com.pcda.common.model.OfficeModel;

import lombok.Data;

@Data
public class TransferOut {

	
	private String message="";
	private String serviceName;
	private String unitServiceName;
	private String unitName;
	private String categoryName;
	private String officeId;
	private String visitorUnitName;
	private String name;
	private String dob;
	private String currentUnit;
	
	private String loggedUserUnitName;
	
	 private String loginServiceName ;
	 
	 private List<OfficeModel> unitList;
	 private String personalNo;
	 private BigInteger userId;
		
		
	
	
	
}
