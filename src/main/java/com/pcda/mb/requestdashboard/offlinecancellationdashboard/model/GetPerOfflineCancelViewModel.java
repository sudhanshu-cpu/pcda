package com.pcda.mb.requestdashboard.offlinecancellationdashboard.model;

import java.util.List;

import com.pcda.util.ApprovalState;
import com.pcda.util.Gender;
import com.pcda.util.ServiceType;
import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class GetPerOfflineCancelViewModel {

	private ServiceType serviceType;

	private String serviceName;
	private String serviceId;

	private String unitServiceName;

	private String categoryName;
	private String categoryId;

	private String rankId;
	private String rankName;

	private String accountOffice;
	private String airAccountOffice;

	private String userAlias;
	private String personalNo;

	private String name;

	private String maritalStatus;

	private String ersPrintName;

	private String dateOfBirth;

	private String dateOfRetirement;

	private String mobileNo;
	private String email;

	private String homeTown;

	private String homeTownNa;

	private String sprNrs;
	private String sprNa;

	private String odsNRS;

	private String odsSprNRS;

	private ApprovalState approvalState;

	private String accountNumber;
	private String ifscCode;

	private Boolean retirementDateUpdated;

	private String signInID;
	private String firstName = "";
	private String middleName = "";
	private String lastName = "";

	private Gender gender;

	private String busPhone;
	private String homePhone;

	private String airId;
	private String groupId;

	private YesOrNo nameEditAllow;
	private YesOrNo cdaoAccEditAllow;

	private Integer retirementAge;

	private String levelId;
	private String levelName;

	private String fax;
	private Integer cvUsed;

	private String nrsDutyStn;
	private String homeTownNrs;
	private String spr;

	private String naDutyStn;
	private String odsNPA;
	private String odsSprNPA;

	private String changeAuthority;

	private String cdaoAccountNo;

	private String dateOfCommissioning;

	private String remarks;
	private String errorMessage;

	private List<LevelInfoOfflineCancelModel> levelInfo;

	private List<DependantOfflineCancModel> familyDetails;

	private Boolean civilianService;
}
