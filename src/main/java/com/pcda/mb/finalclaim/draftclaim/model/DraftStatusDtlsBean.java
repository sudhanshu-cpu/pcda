package com.pcda.mb.finalclaim.draftclaim.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class DraftStatusDtlsBean {

	private BigInteger userID;
	private String approvalState;
	private String actionDate;
	private String remark;
	private Integer seqNo;

}
