package com.pcda.mb.adduser.usermastermissdasboard.model;

import java.math.BigInteger;
import java.util.List;

import com.pcda.mb.requestdashboard.mastermissingdashboard.model.ProfileDetails;
import com.pcda.util.ApprovalState;
import com.pcda.util.PAOVerificationStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class GetUserMasterMissProfileModel {

	private String personalNo;
	private String userAlias;
	private BigInteger userId;
	private String name;
	private String firstName;
	private String middleName;
	private String userServiceId;
	private String lastName;
	private String categoryId;
	private String categoryName;
	private String levelId;
	private String levelName;
	private String unitServiceName;
	private String rankId;
	private String rankName;
	private String serviceId;
	private String serviceName;
	private ApprovalState approvalState;
	private String dateOfBirth;
	private String dateOfRetirment;
	private String commisioningDate;
	private String creationDate;
	private PAOVerificationStatus paoVerificationStatus;
	private String accountOffice;
	private String accountOfficeName;
	private String cdaAccountNumber;
	private String isCivilianService;
	private String airAccountOffice;
	private String phone;
	private String fax;
	private String email;
	private String mobileNo;
	private String userService;
	private String unitService;
	private String railAccountOffice;
	private String nrsRailDutyStn;
	private String nrsRailHomeTown;
	private String naDutyStn;
	private String naHomeTown;
	private String sprSFA;
	private String sprNRS;
	private String sprNA;
	private String hostelNRS;
	private String accountNumber;
	private String cvFormDUsed;
	private String userDetails;
	private String gender;
	private List<ProfileDetails> familyDetails;

}
