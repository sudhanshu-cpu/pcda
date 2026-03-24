package com.pcda.mb.adduser.edittravelerprofile.model;

import java.math.BigInteger;
import java.util.List;

import com.pcda.util.ServiceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditTravelerDTO {

	private BigInteger loginUserId;

	private String ServiceName;

	@NotNull(message = "Service - Cannot be blank")
	private String ServiceId;
	private String alternateService;

//	@NotBlank(message = "Unit - Cannot be blank")
	private String loginVisitorPaoOfficeId;
	@NotBlank(message = "Category - Cannot be blank")
	private String categoryId;
	private String categoryName;
	private String locationId;
	@NotBlank(message = "Rank - Cannot be blank")
	private String rankId;

	private String level;

	private String payAccOff;
	private String airAccOff;
	private String payAcOffName;
	private String airAcOffName;

	private String retireAge = "";

	private String alphaNo = "";
	private String personalNo = "";
	private String chkAlpha = "";

	@NotBlank(message = "First Name - Cannot be blank")
	private String fName = "";
	private String mName = "";
	
	private String lName = "";

	@NotNull(message = "Gender cannot be empty")
	private String gender;
	private String maritalStatus;
	private String dateOfRtirement="";
	private String dateOfBrth = "";
	private String dateOfCom_join = "";
	@NotBlank(message = "Email - Cannot be blank")
	private String email;

	private String userAlias;

	private String isOnExtention = "";
	private String authorityLetterNo;
	private String authorityLetterIssueDate;
	private String extensionStartDate;
	private String extensionEndDate;
	private String authorityDetails;

	private String dutyStnNrs = "";
	private String hmeTwnStnPlace = "";
	private String hmeTwnStnNrs = "";
	private String sprPlace = "";
	private String sprNrs = "";
	private String dutyStnNa = "";
	private String hmeTwnNa = "";
	private String sprNa = "";

	@NotBlank(message = "Mobile - Cannot be blank")
	private String mobileNo;

	private String ersPrntName;
	private String dateOfRetirement;
	private String chkDoughterDependency;
	private String marriedDoughterDependencyType;
	private String isMultipleBerthOfChild = "";
	private String noOfMultipleBerthChild;
	private String isDependentDclrTrue = "";
	private String isUnitInPeaceLoc = "";
	private String isLtcAvailCurrentYear = "";
	private String isLtcAvailCurrentSubBlock = "";
	private String isLtcAvailPreviousSubBlock = "";

	private String currentYear;
	private String currentSubBlockYear = "";
	private String previousSubBlockYear = "";

	private String chkForSpouseService = "";
	private String spousePanNumber = "";
	private String chkForSuspension = "";
	private String chkForAbandoned = "";

	private String lastRowIndex;

	private String accountNumber = "";
	private String ifscCode = "";

	private List<String> firstmemberName;
	private List<String> middlememberName;
	private List<String> lastmemberName;

	private List<String> memGender;
	private List<String> memdob;
	private List<String> memRelation;
	private List<String> memMaritalStatus;
	private List<String> doIIPartNo;
	private List<String> doIIDate;
	private List<String> memReson;
	private List<String> isDepen;
	private List<String> ersPrintName;
	private List<String> childHostelNRS;
	private List<String> childHostelNAP;
	private List<String> mstatus;
	private List<String> opType;
	private List<String> seqNo;
	private List<String> memSeqNo;
	private List<String> remark;



	private String cv_fd_used = "";
	private String acNo = "";
	private ServiceType serviceType;

	private String airForceCadetNo = "";
	private String cadetNo = "";
	private String cadetChkAlpha = "";
	private String courseSerialNo = "";

	private String travllerId;



	private String rowNumber;

	private String retirementChangeReason = "";
	private String retirementChangeAuthority = "";

	private String cdaAccountNumber = "";

	private String changeAuthority = "";

	private String odsNRS;
	private String odsNPA;
	private String odsSprNRS;
	private String odsSprNPA;

	private String noOffamilyMem;


}
