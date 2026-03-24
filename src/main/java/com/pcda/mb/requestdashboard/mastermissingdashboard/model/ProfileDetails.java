package com.pcda.mb.requestdashboard.mastermissingdashboard.model;

import java.util.Date;

import lombok.Data;

@Data
public class ProfileDetails {
	
	private Integer seqNumber;
	private String modifiedBy;
	private String createdDate;
	private String modifiedDate;
	private String createdBy;
	private String firstName;
	private String middleName;
	private String lastName;
	private String relation;
	private Date dob;
	private String dobStr;
	private String gender;
	private String maritalStatus;
	private String partNo;
	private Date partDate;
	private String partDateStr;
	private String reason;
	private String ersPrintName;
	private String status;
	private String isActive;
	private String remark;
	private String hostelNRS;
	private String hostelNAP;

}
