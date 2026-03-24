package com.pcda.common.model;

import java.math.BigInteger;
import java.util.Date;

import com.pcda.util.Status;
import com.pcda.util.YesOrNo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TravelerProfileModel {

	private Date commisioningDate;
	private String accountNumber;
	private String accountOffice;
	private String airAccountOffice;
	private String homeTown;
	private String nrsHomeTown;
	private String nrsDutyStation;
	private String doTwoNumber;
	private Date doTwoDate;
	private Long cvFormDUsed;
	private Long ltcAvailed;
	private String sprSFA;
	private String sprNRS;
	private String personalNoBlock;
	private Status status;
	private BigInteger modifiedBy;
	private Date modifiedDate;
	private BigInteger approvedBy;
	private String ersPrintName;
	private String oldNRSDutyStation;
	private String remark;
	private String sprNA;
	private String homeTownNA;
	private String dutyStationNA;
	private YesOrNo abandoned;
	private YesOrNo onSuspension;
	private YesOrNo spouseGovEmployee;
	private String ltcCurrentSubBlock;
	private String ltcCurrentYear;
	private String ltcPreviousSubBlock;
	private String spousePanNo;
	private String hostelNRS;
	private String oldAccountNumber;
	private String odsNRS;
	private String odsNA;
	private String odsSPRNrs;
	private String odsSPRNa;
	private String extentionLetterNo;
	private String extentionAuthority;
	private Date extentionIssuedOn;
	private Date extentionFrom;
	private Date extentionUpto;
	
}
