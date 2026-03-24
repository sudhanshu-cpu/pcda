package com.pcda.mb.adduser.changepersonalno.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetChangePersonalNo {

	private String userAlias;
	private String userAliasEnc;
	private Date enrollmentDate;
	private BigInteger loginUserId;
	private Date dob;
	private String personalNoChanged;
	private String groupId;
	private String unitName;
	private String unitId;
	private String name;
	private String dobStr;
	private String enrollmentDateStr;

}
