package com.pcda.mb.adduser.usermastermissdasboard.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSettleUserMasterMissStatusModel {
	
	private String userAlias;
	private String comment;
	private BigInteger commentBy;
	private String personalNo;
	private String payAccOff;
	private String airAccOff;
	private Integer seqNumber;
	private BigInteger loginUserId;
	private BigInteger modifiedBy ;

	private String comunctionDate;
	
	
}



