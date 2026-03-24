package com.pcda.co.approveuser.approvebulktransferinreemployment.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class BulkTransferInReEmpViewDtlsBean {
	
	private BigInteger userId;
	private String personalNo;
	private String sosDate;
	private String retirementDate;

}
