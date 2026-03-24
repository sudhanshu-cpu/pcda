package com.pcda.common.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.pcda.util.ApprovalState;
import com.pcda.util.DODUserType;
import com.pcda.util.Gender;
import com.pcda.util.MaritalStatus;
import com.pcda.util.PAOVerificationStatus;
import com.pcda.util.UserStatus;
import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class User {

	private BigInteger userId;
	private String userAlias;
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
	private Integer numberLogin;
	private Date lastModTime;
	private String mobileNo;
	private String categoryId;
	private String serviceId;
	private String userServiceId;
	private String rankId;
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
	private Integer loginAttempt;
	private Date passModiDate;
	private Date sosDate;
	private Date joiningDate;
	private Date creationDate;
	private String agentId;
	private PAOVerificationStatus paoVerificationStatus;
	private YesOrNo gradePayUpdateFlag;
	private YesOrNo txnDone;
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
	private UserStatus userStatus;
	private String levelName;
	private ApprovalState profileStatus;
	
	
	private List<UserRole> userRole;


}
