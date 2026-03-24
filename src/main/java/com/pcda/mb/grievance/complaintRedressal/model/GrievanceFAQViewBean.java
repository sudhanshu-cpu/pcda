package com.pcda.mb.grievance.complaintRedressal.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class GrievanceFAQViewBean {
	
	private BigInteger grievanceCatId;
	private BigInteger grievanceFAQId;
	private String question;
	private String answer;
	
}
