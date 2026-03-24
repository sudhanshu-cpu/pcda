package com.pcda.pao.reports.userverification.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class UserVerificationConfirmModel {

	private BigInteger userId;
	private String name;
	private String personalNo;
	
	private String unitName;
	private String categoryName;
	
	private String serviceName;
	private String rankName;
	
	private String levelName;
	private Date creationDate;
	
	private String formatCreationDate; 
	
	private String profileStatus;
	private String comment;
	
}



