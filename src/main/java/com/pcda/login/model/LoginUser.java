package com.pcda.login.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.pcda.common.model.SecurityQuestionModel;
import com.pcda.common.model.UserRole;
import com.pcda.util.ApprovalState;
import com.pcda.util.DODUserType;
import com.pcda.util.YesOrNo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUser implements Serializable{

	private static final long serialVersionUID = -4804674431357861549L;

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
		private String gender;
		private String maritalStatus;
		private Date lastLoginDate;
		private Integer numberLogin;
		private Date lastModTime;
		private String mobileNo;
		private String categoryId;
		private String serviceId;
		private String userServiceId;
		private String rankId;
		private String personalNoChanged;
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
		private String paoVerificationStatus;
		private String gradePayUpdateFlag;
		private String txnDone;
		private String levelId;
		private Date contactInfoModDate;
		private String retirementChangeReason;
		private String retirementChangeAuthority;
		private String requestBlock;
		private String requestBlockRemark;
		private String nameEditAllow;
		private String cdaoAccEditAllow;
		private String bankAccountNumber;
		private String ifscCode;
		private String userStatus;
		private Set<UserRole> userRole=new LinkedHashSet<>();
		private Set<SecurityQuestionModel> securityQuestions;
		private Integer serviceProvider;
		

}
