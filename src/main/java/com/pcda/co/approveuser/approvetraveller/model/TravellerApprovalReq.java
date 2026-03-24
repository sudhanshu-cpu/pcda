package com.pcda.co.approveuser.approvetraveller.model;

import java.util.List;

import lombok.Data;

@Data
public class TravellerApprovalReq {

	private String userAlias;
	private String name;
	private String gender;

	private String email;
	private String firstName;
	private String middleName;
	private String lastName;
	private String mobileNo;

	private String userService;
	private String unitService;
	private String category;
	private String levelName;
	private String rank;

	private String dob;
	private String dateOfRetirement;
	private String dateOfEnrolment;

	private String cdaAcNo;
	private Integer cvfdCount;

	private String railPayAccountOffice;
	private String airPayAccountOffice;
	private String nrsRailDutyStn;
	private String nrsRailHomeTown;
	private String nrsrHomeTown;
	private String naDutyStn;
	private String naHomeTown;
	private String nrsSpr;
	private String nrsrSpr;
	private String naSpr;
	private String chNrs;
	private String accountNo;
	private String ifscCode;

	private String unitNo;
	private String serviceNo;

	private List<TravellerApprovalDep> dependents;

}
