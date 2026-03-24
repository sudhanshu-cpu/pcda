package com.pcda.mb.adduser.disapprovedprofile.model;

import com.pcda.util.Status;

import lombok.Data;

@Data
public class ProfileDependantDtls {

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
	private Status status;
	private String remark;
	private String childHostelNRS;
	private String childHostelNAP;
	private String doiipartNo;
	private String doiidate;

}
