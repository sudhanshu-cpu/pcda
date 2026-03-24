package com.pcda.common.model;

import java.math.BigInteger;
import java.util.Date;

import com.pcda.util.Gender;
import com.pcda.util.MaritalStatus;
import com.pcda.util.RelationType;
import com.pcda.util.Status;
import com.pcda.util.YesOrNo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamilyDetailsModel {

	private Integer seqNumber;
	private BigInteger modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	private BigInteger createdBy;
	private String firstName;
	private String middleName;
	private String lastName;
	private RelationType relation;
	private Date dob;
	private Gender gender;
	private MaritalStatus maritalStatus;
	private String partNo;
	private Date partDate;
	private String reason;
	private String ersPrintName;
	private Status status;
	private YesOrNo isActive;
	private String remark;
	private String hostelNRS;
	private String hostelNAP;
	
}
