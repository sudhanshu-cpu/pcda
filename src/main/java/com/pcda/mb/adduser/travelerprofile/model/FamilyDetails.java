package com.pcda.mb.adduser.travelerprofile.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class FamilyDetails {

	private Integer seqNo;

	private String firstName = "";
	private String middleName = "";
	private String lastName = "";

	private int status = 1;
	private int gender;
	private int relation;
	private int maritalStatus;
	private String memRelation = "";

	private Date dob;

	private Date partDate;
	private String partNo;

	private String reason = "";
	private String ersPrintName;

	private String hostelNRS = "";
	private String hostelNAP = "";

	private BigInteger modifiedBy;
	private BigInteger createdBy;

}
