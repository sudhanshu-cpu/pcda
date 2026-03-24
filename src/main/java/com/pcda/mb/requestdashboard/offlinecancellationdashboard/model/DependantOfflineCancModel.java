package com.pcda.mb.requestdashboard.offlinecancellationdashboard.model;

import lombok.Data;

@Data
public class DependantOfflineCancModel {

	private Integer seqNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dateOfBirth;
	private String gender;
	private Integer genderCode;
	private String relation;
	private Integer relationCode;
	private String maritalStatus;
	private Integer maritalStatusCode;
	private String ersPrintName;
	private String reason;
	private String status;
	private String remark;
	private String childHostelNRS;
	private String childHostelNAP;
	private String doiipartNo;
	private String doiidate;
}
