package com.pcda.mb.requestdashboard.mastermissingdashboard.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostMasterMissingModel {
	
	private String personalNo;
	private String comment;
	private String groupId;
	private BigInteger loginUserId;
	
	 

}
