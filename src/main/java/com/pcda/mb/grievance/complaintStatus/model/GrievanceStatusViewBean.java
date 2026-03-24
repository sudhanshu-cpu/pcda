package com.pcda.mb.grievance.complaintStatus.model;

import java.util.Date;

import com.pcda.util.GrievanceStatus;

import lombok.Data;

@Data
public class GrievanceStatusViewBean {

	private Date actionDate;
	private GrievanceStatus currentSatus;
	private String remark;
	private int sequence;

	
}
