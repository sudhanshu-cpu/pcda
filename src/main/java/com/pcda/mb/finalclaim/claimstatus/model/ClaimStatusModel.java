package com.pcda.mb.finalclaim.claimstatus.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class ClaimStatusModel {

	private BigInteger loginUserId;
	
	private String tadaClaimId;
	private String personalNo;
	private String groupID;
	private String claimType;
	private String details;
	private String travelID;
	private String claimReceivedDate;
	private String status;
	private List<TadaClaimStatus>tadaClaimStatus;
	
}







   
    