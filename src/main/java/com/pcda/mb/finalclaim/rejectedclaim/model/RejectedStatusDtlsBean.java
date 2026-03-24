package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class RejectedStatusDtlsBean {

	private BigInteger userID;
	private String approvalState;
	private String actionDate;
	private String remark;
	private Integer seqNo;

}
