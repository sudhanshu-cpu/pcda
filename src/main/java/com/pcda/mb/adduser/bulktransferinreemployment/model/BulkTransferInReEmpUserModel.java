package com.pcda.mb.adduser.bulktransferinreemployment.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class BulkTransferInReEmpUserModel {
	
	private BigInteger userId;
	private String sosDate;
	private String dateOfRetirement;
	private String serviceIds;
	private String unitId;
	
	

}
