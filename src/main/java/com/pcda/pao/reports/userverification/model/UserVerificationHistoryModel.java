package com.pcda.pao.reports.userverification.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class UserVerificationHistoryModel {
		
		private String userAlias;
		private String comment;
		private String commentBy;
		private String personalNo;
		private String payAccOff;
		private String airAccOff;
		private Integer seqNumber;
		private BigInteger loginUserId;
		private BigInteger modifiedBy;

		private Date comunctionDate;
		private String comunctionDateStr;
		
		
		   
		
}
