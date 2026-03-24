package com.pcda.mb.adduser.transferin.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class TransferIN {
	
	private String message="";
	private String serviceName;
	private String unitServiceName;
	private String unitName;
	private String categoryName;
	private String officeId;
	private String visitorUnitName;
	private String name;
	private String dob;
	private BigInteger userId;
	private String loggedUserUnitName;
	
	 private String loginServiceName ;
	 private String personalNo;
	
	 private String currentUnit;
	
}
