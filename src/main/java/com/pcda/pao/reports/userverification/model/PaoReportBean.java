package com.pcda.pao.reports.userverification.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class PaoReportBean {
	
	private BigInteger userId;
	private String personalNo;
	private String fullName;
	private String unitName;
	private String categoryName;
	private String serviceName;
	private String rankName;
	private String levelName;
	private Date creationDate;
	private String creationDateFormate;
	private String profileStatusStr;
	private Integer profileStatus;
	private Integer dodUserType;
	private String approvalState;
	private String unitGroupId;
	private String accountOficeName;
	private String accountOficeId;
	private String serviceId;
	private String categoryId;
	private String altServiceName;
	private String altServiceId;
	private String rankId;
	private String levelId;
	private String cdaoAccountNo;
	private Date  dateOfBirth;
	private Date dateOfRetirement;
	
}
