package com.pcda.mb.adduser.travelerprofile.model;

import java.math.BigInteger;
import java.util.List;

import com.pcda.util.ServiceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TravelerProfileDTO {

	private BigInteger loginUserId;

	private String loginVisitorServiceName;

	@NotNull(message = "Service - Cannot be blank")
	private String loginVisitorServiceId;
	private String service;
	private String alternateService;

	@NotBlank(message = "Unit - Cannot be blank")
	private String loginVisitorUnitId;
	private String loginVisitorPaoOfficeId;
	@NotBlank(message = "Category - Cannot be blank")
	private String categoryId;
	private String categoryName;
	private String locationId;
	@NotBlank(message = "Rank - Cannot be blank")
	private String rankId;
	@NotBlank(message = "Level - Cannot be blank")
	private String level;

	private String payAcOff;
	private String airAcOff;
	private String payAcOffName;
	private String airAcOffName;

	private String retireAge = "";

	private String alphaNo = "";
	private String personalNo = "";
	private String chkAlpha = "";

	@NotBlank(message = "First Name - Cannot be blank")
	private String fName = "";
	private String mName = "";
	@NotBlank(message = "Last Name - Cannot be blank")
	private String lName = "";

	@NotNull(message = "Gender cannot be empty")
	private int gender;
	private int maritalStatus;
	private String dob = "";
	private String dateOfCom_join = "";
	@NotBlank(message = "Email - Cannot be blank")
	private String email;

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
	private String mobNo;

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
	private String ltcAvailPreviousYear="";
	private String isLtcAvailPreviousYear="";

	private String currentYear;
	private String currentSubBlockYear = "";
	private String previousSubBlockYear = "";

	private String chkForSpouseService = "";
	private String spousePanNumber = "";
	private String chkForSuspension = "";
	private String chkForAbandoned = "";

	private int lastRowIndex;

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
	private List<String> status;



	private String cv_fd_used = "";
	private String acNo = "";
	private ServiceType serviceType;

	private String airForceCadetNo = "";
	private String cadetNo = "";
	private String cadetChkAlpha = "";
	private String courseSerialNo = "";

	private String travllerId;

	
	private String civilianPersonalNo;

}
