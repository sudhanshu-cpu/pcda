package com.pcda.mb.adduser.changeservice.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data

public class GetChangeServiceViewModel {

private BigInteger loginUserId;
	
	
	private String oldUnitService;	
	private String oldCategory;
	private String oldUserService;
	private String reason;
	private String oldRank;
	private String oldPayAccOff;
	private Date creationDate;
	
	private String creationDateStr;
	private String oldLevel;
	
	private String newPayAcOff;
	private String airAcOff;
	private String newLevelName;
	
	private String personalNumber;
	private String newUserService;	
	private String newUnitService;
	private String categoryId;
	private String rankId;
	private String personalName;
	
	
	
}
