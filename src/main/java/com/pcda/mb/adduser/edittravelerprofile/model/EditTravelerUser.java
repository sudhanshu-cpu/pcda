package com.pcda.mb.adduser.edittravelerprofile.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class EditTravelerUser {

	List<EditFamilyDtls> familyDetails;

private String	dateOfRetirement;
 private String 	  dateOfBirth;
	  private String alternateService;
	private String   isMasterPhysicallyChallanged;
	  private String masterDisableCategoryId;
private String	  masterCardNumber;
private String masterCardExpiryDate;
	private Integer   spouseOnGovtServiceFlag;
	private String	spousePanNumber;
	private String	suspensionFlag;
			private Integer	  abandonedFlag;
	  private String	currentYear;
	  private String	currentSubBlockYear;
	  private String	previousSubBlockYear;
	  private String	isOnExtention;
	  private String	authorityLetterNo;
	  private String	authorityLetterIssueDate;
	  private String	extensionStartDate;
	  private String	extensionEndDate;
	  private String	authorityDetails;
	  private String	airAcOff;
	  private String	categoryName;
	  private String	rankName;
	  private String	level;
	  private String	retdCngReason;
	  private String	retdCngAuthority;
	  private String	serviceName;
	  private String	changeAuthority;
	  private String	odsNRS;
	  private String	odsNPA;
	  private String	odsSprNRS;
	  private String	odsSprNPA;
	  private String	accountNumber;
	  private String	ifscCode;
	  private String	serviceId;

	  private Boolean  dependentConfirmationTrue;
	  private Boolean multipleBerthOfChild;
	 private Boolean  dependentMarriedDoughter;

	 private String	lname;
	  private String	fname;
	  private String	mname;

	  private Integer		rowNum;
		private Integer		  memSeqNo;
		private Integer	seqNo;
	private Integer age;
	private Integer  passengerType;
	private Integer   noOffamilymemDeleted;
	private BigInteger   loginUserId;
	private Integer  isValidIdProof;

	  private String   gender;
	  private String dob;
	  private String relation;
	  private String maritalStatus;
	  private String income;
	  private String status;
	  private String emailId;
	  private String mobNo;
	  private String telNo1;
	  private String stdCdeNo1;
	  private String telNo2;
	  private String stdCdeNo2;
	  private String reason;
	  private String  doIIPartNo;
	  private String doIIDate;
	  private String  isDependant;

	  private Boolean	  checkStatus;

	  private String  ersPrintName;
	  private String  	  opType;
	 private String  	  otherRelation;

	 private String  	  	 remark;
	 private String  	   pxnDateOfBirth;
	 private String  	   childHostelNRS;
	 private String  	  childHostelNAP;
	 private String  	  userAlias;

	 private String  	  alphaNo;
	 private String personalNo;
	 private String chkAlpha;
	 private String userId;
	 private String travellerId;
	 private String category;
	 private String services;
	 private String rank;
	 private String acNo;
	 private String dateOfCom_join;
	private String dateOfComJoin;
	 private String cv_fd_used;
	 private String chkLtcForDependent;
	 private String isUnitInPeaceLoc;
	 private String payAcOff;
	 private String isDutyStn;
	 private String cdaAccNo;
	 private String isHmeTown;
	 private String isSpr;
	 private String dutyStnPlace;
	 private String hmeTownPlace;
	 private String sprPlace;
	 private String dutyStnNrs;
	 private String hmeTownNrs;
	 private String sprNrs;
	 private String dutyStnNa;
	 private String hmeTwnNa;
	 private String sprNa;
	 private String doPart2;
	 private String availedLtc;
	 private String cvUsed;
	 private String formDUnused;
	 private String childrenDateOfBirth;
	 private String marriedDoughterDependencyType;
	 private String noOfMultipleBerthChild;

}
