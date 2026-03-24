package com.pcda.mb.grievance.complaintHistory.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class GrievancePostBean {

	private String grievanceId;
	private Integer department;
	private String personalNo;
	private BigInteger userId;
     private List<Integer> grievanceStatus;
     private String groupId;
	
}
