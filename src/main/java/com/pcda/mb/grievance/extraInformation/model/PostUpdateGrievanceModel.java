package com.pcda.mb.grievance.extraInformation.model;

import java.math.BigInteger;
import java.util.List;

import com.pcda.mb.grievance.complaintRedressal.model.FileDetails;

import lombok.Data;

@Data
public class PostUpdateGrievanceModel {
	
	private String grievanceId;
	private String grievance;
	private BigInteger loginUserId;
	private Integer grievanceStatus=4;
	private List<FileDetails> fileDetails;
	
	
}
