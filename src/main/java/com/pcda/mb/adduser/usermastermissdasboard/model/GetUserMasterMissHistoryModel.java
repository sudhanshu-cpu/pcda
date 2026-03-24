package com.pcda.mb.adduser.usermastermissdasboard.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserMasterMissHistoryModel {
	
	private String userAlias;
	private String comment;
	private String commentBy;
	private String personalNo;
	private String payAccOff;
	private String airAccOff;
	private Integer seqNumber;
	private BigInteger loginUserId;
	private BigInteger modifiedBy;

	private String comunctionDateStr;
	private String comunctionDate;
	
	   
	
}
