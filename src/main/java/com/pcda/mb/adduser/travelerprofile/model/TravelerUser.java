package com.pcda.mb.adduser.travelerprofile.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TravelerUser {

	private List<SecurityQuestions> securityQuestions;
	private List<FamilyDetails> familyDetails;
	private TravelerProfileModel travelerProfile;
	private String userRoleType = "TRAVELER_USER";
	private int userType = 1;
	private BigInteger loginUserId;
	private String remarks;
	private String firstName;
	private String middleName;
	private String lastName;
	private String personalNumber;
	private String userAlias;
	private String categoryId;
	private String categoryName;
	private String serviceId;
	private String levelId;
	private String userServiceId;
	private String rankId;
	private String unitId;
	private int gender;
	private String mobileNo;
	private String email;
	private int maritalStatus;
	private Date dateOfRetirement;
	private Date dateOfBirth;
	private Date joiningDate;
	private String bankAccountNumber="";
	private String ifscCode="";
	private Integer paoVerificationStatus;
	
	
	



}
