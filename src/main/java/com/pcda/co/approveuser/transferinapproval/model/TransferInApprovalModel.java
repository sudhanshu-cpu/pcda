package com.pcda.co.approveuser.transferinapproval.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class TransferInApprovalModel {
	
	private String userAlias;
	private BigInteger userId;
	private String firstName;
	private String middleName;
	private String lastName;
	
	private int seqNo;
	private String oldProfileAtrribute;
	private String profileChangeAtrribute;
	private String modifiedBy;
	
	private String createdBy;
	private String[] profileChangeAtrributeSplit;
	
	private String profileAttributeName;
	
	
}
