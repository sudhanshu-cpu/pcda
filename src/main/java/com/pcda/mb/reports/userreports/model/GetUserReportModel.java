package com.pcda.mb.reports.userreports.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pcda.util.ApprovalState;
import com.pcda.util.DODUserType;
import com.pcda.util.Gender;
import com.pcda.util.MaritalStatus;
import com.pcda.util.PAOVerificationStatus;
import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class GetUserReportModel {

	private BigInteger userId;
	private String userAlias;
	private String password;
	private String name;
	private String firstName;
	private String middleName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String email;
	private Gender gender;
	private MaritalStatus maritalStatus;
	private Date lastLoginDate;
	private Date lastModTime;
	private String mobileNo;
	private String categoryId;
	private String categoryName;
	private String serviceId;
	private String serviceName;
	private String userServiceId;
	private String rankId;
	private String personalNumber;
	private YesOrNo personalNoChanged;
	private DODUserType userType;
	private String digiCertDetails;
	private YesOrNo coActive;
	private ApprovalState approvalState;
	private BigInteger lastModBy;
	private String remarks;
	private BigInteger approvedBy;
	private Date dateOfBirth;
	private Date dateOfRetirement;
	private Date sosDate;
	private Date joiningDate;
	private Date creationDate;
	private String agentId;
	
	private PAOVerificationStatus paoVerificationStatus;
	private String levelId;
	private Date contactInfoModDate;
	private String retirementChangeReason;
	private String retirementChangeAuthority;
	private YesOrNo requestBlock;
	private String requestBlockRemark;
	private YesOrNo nameEditAllow;
	private YesOrNo cdaoAccEditAllow;
	private String bankAccountNumber;
	private String ifscCode;
	private String userStatus;
	private String unitId;
	private String oldPassword;
	private int serviceProvider;
	
	private List<UserRoleModel> userRole = new ArrayList<>();

	private TravellerProfileModel travelerProfile;

}
